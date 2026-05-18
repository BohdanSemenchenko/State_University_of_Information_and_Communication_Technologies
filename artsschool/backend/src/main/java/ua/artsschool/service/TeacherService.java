package ua.artsschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.artsschool.dto.*;
import ua.artsschool.exception.ResourceNotFoundException;
import ua.artsschool.model.Teacher;
import ua.artsschool.repository.TeacherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final MapperService mapper;

    public TeacherResponse create(TeacherRequest req) {
        Teacher t = Teacher.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .phone(req.getPhone())
                .email(req.getEmail())
                .disciplines(req.getDisciplines() != null ? new ArrayList<>(req.getDisciplines()) : new ArrayList<>())
                .build();
        return mapper.toTeacherResponse(teacherRepository.save(t));
    }

    @Transactional(readOnly = true)
    public List<TeacherResponse> getAll() {
        return teacherRepository.findAll().stream().map(mapper::toTeacherResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeacherResponse> getActive() {
        return teacherRepository.findByActiveTrue().stream().map(mapper::toTeacherResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeacherResponse getById(Long id) {
        return mapper.toTeacherResponse(teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Викладач", id)));
    }

    public TeacherResponse update(Long id, TeacherRequest req) {
        Teacher t = teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Викладач", id));
        if (req.getFirstName() != null) t.setFirstName(req.getFirstName());
        if (req.getLastName() != null) t.setLastName(req.getLastName());
        if (req.getPhone() != null) t.setPhone(req.getPhone());
        if (req.getEmail() != null) t.setEmail(req.getEmail());
        if (req.getDisciplines() != null) t.setDisciplines(new ArrayList<>(req.getDisciplines()));
        return mapper.toTeacherResponse(teacherRepository.save(t));
    }

    public void deactivate(Long id) {
        Teacher t = teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Викладач", id));
        t.setActive(false);
        teacherRepository.save(t);
    }
}
