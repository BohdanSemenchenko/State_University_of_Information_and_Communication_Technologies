package ua.artsschool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.artsschool.dto.*;
import ua.artsschool.exception.*;
import ua.artsschool.model.*;
import ua.artsschool.repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final MapperService mapper;

    // ── Groups CRUD ─────────────────────────────────────────────────────────

    public GroupResponse create(GroupRequest req) {
        if (req.getMinCapacity() > req.getMaxCapacity())
            throw new BusinessException("Мінімум не може бути більшим за максимум");

        Teacher teacher = null;
        if (req.getTeacherId() != null) {
            teacher = teacherRepository.findById(req.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Викладач", req.getTeacherId()));
        }

        Group g = Group.builder()
                .name(req.getName())
                .discipline(req.getDiscipline())
                .teacher(teacher)
                .minCapacity(req.getMinCapacity())
                .maxCapacity(req.getMaxCapacity())
                .schedule(req.getSchedule())
                .ageRange(req.getAgeRange())
                .classroom(req.getClassroom())
                .build();

        return mapper.toGroupResponse(groupRepository.save(g), false);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getAll() {
        return groupRepository.findAll().stream()
                .map(g -> mapper.toGroupResponse(g, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponse getById(Long id) {
        Group g = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Група", id));
        return mapper.toGroupResponse(g, true);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getAvailable() {
        return groupRepository.findAvailable().stream()
                .map(g -> mapper.toGroupResponse(g, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getByDiscipline(ArtDiscipline discipline) {
        return groupRepository.findByDiscipline(discipline).stream()
                .map(g -> mapper.toGroupResponse(g, false))
                .collect(Collectors.toList());
    }

    public GroupResponse update(Long id, GroupRequest req) {
        Group g = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Група", id));
        if (req.getName() != null) g.setName(req.getName());
        if (req.getSchedule() != null) g.setSchedule(req.getSchedule());
        if (req.getAgeRange() != null) g.setAgeRange(req.getAgeRange());
        if (req.getClassroom() != null) g.setClassroom(req.getClassroom());
        if (req.getTeacherId() != null) {
            Teacher t = teacherRepository.findById(req.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Викладач", req.getTeacherId()));
            g.setTeacher(t);
        }

        if (req.getMinCapacity() > 0 && req.getMaxCapacity() > 0) {
            if (req.getMinCapacity() > req.getMaxCapacity()) {
                throw new BusinessException("Мінімум не може бути більшим за максимум");
            }
            g.setMinCapacity(req.getMinCapacity());
            g.setMaxCapacity(req.getMaxCapacity());
        }

        g.autoUpdateStatus();
        return mapper.toGroupResponse(groupRepository.save(g), false);
    }

    public void updateCapacity(Long id, int min, int max) {
        if (min > max) throw new BusinessException("Мінімум не може бути більшим за максимум");
        Group g = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Група", id));
        g.setMinCapacity(min);
        g.setMaxCapacity(max);
        g.autoUpdateStatus();
        groupRepository.save(g);
        log.info("Capacity updated for group '{}': {}-{}", g.getName(), min, max);
    }

    public void changeStatus(Long id, Group.GroupStatus status) {
        Group g = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Група", id));
        g.setStatus(status);
        groupRepository.save(g);
    }

    // ── Enrollment ──────────────────────────────────────────────────────────

    public EnrollmentResponse enroll(Long studentId, Long groupId, String notes) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Учень", studentId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Група", groupId));

        if (!group.canEnroll())
            throw new BusinessException("Група '" + group.getName() + "' недоступна для запису (статус: " + group.getStatus().getDisplayName() + ")");

        if (enrollmentRepository.existsByStudentIdAndGroupIdAndStatus(studentId, groupId, Enrollment.EnrollmentStatus.ACTIVE))
            throw new BusinessException("Учень вже навчається у цій групі");

        group.getStudents().add(student);
        group.autoUpdateStatus();
        groupRepository.save(group);

        Enrollment e = Enrollment.builder().student(student).group(group).notes(notes).build();
        Enrollment saved = enrollmentRepository.save(e);

        log.info("Enrolled {} into group '{}'. Fill: {}/{}", student.getFullName(), group.getName(),
                group.getCurrentSize(), group.getMaxCapacity());

        return mapper.toEnrollmentResponse(saved);
    }

    public void withdraw(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Група", groupId));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Учень", studentId));

        Enrollment e = enrollmentRepository.findByStudentIdAndGroupIdAndStatus(studentId, groupId, Enrollment.EnrollmentStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException("Активного зарахування не знайдено"));

        group.getStudents().remove(student);
        group.autoUpdateStatus();
        groupRepository.save(group);

        e.setStatus(Enrollment.EnrollmentStatus.WITHDRAWN);
        e.setWithdrawalDate(LocalDate.now());
        enrollmentRepository.save(e);

        log.info("Withdrew {} from group '{}'", student.getFullName(), group.getName());
    }

    public EnrollmentResponse transfer(Long studentId, Long fromGroupId, Long toGroupId) {
        withdraw(studentId, fromGroupId);
        EnrollmentResponse result = enroll(studentId, toGroupId, "Переведено з групи ID " + fromGroupId);
        log.info("Transferred student {} from group {} to group {}", studentId, fromGroupId, toGroupId);
        return result;
    }

    // ── Dashboard ───────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public DashboardStats getDashboard() {
        List<Group> groups = groupRepository.findAll();
        long full = groups.stream().filter(Group::isFull).count();
        long underfilled = groups.stream().filter(g ->
                g.isUnderfilled() && g.getStatus() != Group.GroupStatus.CLOSED).count();
        long forming = groups.stream().filter(g -> g.getCurrentSize() == 0).count();
        long active = groupRepository.countByStatus(Group.GroupStatus.ACTIVE);
        double avgFill = groups.isEmpty() ? 0 :
                Math.round(groups.stream().mapToDouble(Group::getFillRate).average().orElse(0) * 10.0) / 10.0;

        List<GroupCapacityInfo> caps = groups.stream()
                .map(mapper::toCapacityInfo)
                .collect(Collectors.toList());

        return DashboardStats.builder()
                .totalStudents(studentRepository.count())
                .activeStudents(studentRepository.countByStatus(Student.StudentStatus.ACTIVE))
                .totalTeachers(teacherRepository.count())
                .activeTeachers(teacherRepository.countByActiveTrue())
                .totalGroups(groups.size())
                .activeGroups(active)
                .fullGroups(full)
                .underfilledGroups(underfilled)
                .formingGroups(forming)
                .avgFillRate(avgFill)
                .groupCapacities(caps)
                .build();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(mapper::toEnrollmentResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getEnrollmentsByGroup(Long groupId) {
        return enrollmentRepository.findByGroupId(groupId).stream()
                .map(mapper::toEnrollmentResponse).collect(Collectors.toList());
    }
}
