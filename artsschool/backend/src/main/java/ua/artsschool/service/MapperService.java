package ua.artsschool.service;

import org.springframework.stereotype.Component;
import ua.artsschool.dto.*;
import ua.artsschool.model.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperService {

    public StudentResponse toStudentResponse(Student s) {
        if (s == null) return null;
        return StudentResponse.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .fullName(s.getFullName())
                .birthDate(s.getBirthDate())
                .phone(s.getPhone())
                .email(s.getEmail())
                .parentName(s.getParentName())
                .parentPhone(s.getParentPhone())
                .status(s.getStatus())
                .createdAt(s.getCreatedAt())
                .build();
    }

    public TeacherResponse toTeacherResponse(Teacher t) {
        if (t == null) return null;
        return TeacherResponse.builder()
                .id(t.getId())
                .firstName(t.getFirstName())
                .lastName(t.getLastName())
                .fullName(t.getFullName())
                .phone(t.getPhone())
                .email(t.getEmail())
                .disciplines(t.getDisciplines())
                .active(t.isActive())
                .build();
    }

    public GroupResponse toGroupResponse(Group g, boolean includeStudents) {
        if (g == null) return null;
        List<StudentResponse> students = includeStudents && g.getStudents() != null
                ? g.getStudents().stream().map(this::toStudentResponse).collect(Collectors.toList())
                : List.of();
        return GroupResponse.builder()
                .id(g.getId())
                .name(g.getName())
                .discipline(g.getDiscipline())
                .disciplineDisplayName(g.getDiscipline().getDisplayName())
                .teacher(toTeacherResponse(g.getTeacher()))
                .minCapacity(g.getMinCapacity())
                .maxCapacity(g.getMaxCapacity())
                .currentSize(g.getCurrentSize())
                .availableSlots(g.getAvailableSlots())
                .fillRate(Math.round(g.getFillRate() * 10.0) / 10.0)
                .status(g.getStatus())
                .statusDisplayName(g.getStatus().getDisplayName())
                .schedule(g.getSchedule())
                .ageRange(g.getAgeRange())
                .classroom(g.getClassroom())
                .students(students)
                .build();
    }

    public GroupCapacityInfo toCapacityInfo(Group g) {
        return GroupCapacityInfo.builder()
                .id(g.getId())
                .name(g.getName())
                .discipline(g.getDiscipline().getDisplayName())
                .disciplineKey(g.getDiscipline().name())
                .currentSize(g.getCurrentSize())
                .maxCapacity(g.getMaxCapacity())
                .minCapacity(g.getMinCapacity())
                .fillRate(Math.round(g.getFillRate() * 10.0) / 10.0)
                .status(g.getStatus().name())
                .statusDisplayName(g.getStatus().getDisplayName())
                .build();
    }

    public EnrollmentResponse toEnrollmentResponse(Enrollment e) {
        return EnrollmentResponse.builder()
                .id(e.getId())
                .student(toStudentResponse(e.getStudent()))
                .groupId(e.getGroup().getId())
                .groupName(e.getGroup().getName())
                .enrollmentDate(e.getEnrollmentDate())
                .withdrawalDate(e.getWithdrawalDate())
                .status(e.getStatus())
                .notes(e.getNotes())
                .build();
    }
}
