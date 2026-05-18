package ua.artsschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.artsschool.dto.*;
import ua.artsschool.exception.ResourceNotFoundException;
import ua.artsschool.model.Student;
import ua.artsschool.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final MapperService mapper;

    public StudentResponse create(StudentRequest req) {
        Student s = Student.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .birthDate(req.getBirthDate())
                .phone(req.getPhone())
                .email(req.getEmail())
                .parentName(req.getParentName())
                .parentPhone(req.getParentPhone())
                .build();
        return mapper.toStudentResponse(studentRepository.save(s));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream().map(mapper::toStudentResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        return mapper.toStudentResponse(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Учень", id)));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> search(String q) {
        return studentRepository.searchByName(q).stream().map(mapper::toStudentResponse).collect(Collectors.toList());
    }

    public StudentResponse update(Long id, StudentRequest req) {
        Student s = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Учень", id));
        if (req.getFirstName() != null) s.setFirstName(req.getFirstName());
        if (req.getLastName() != null) s.setLastName(req.getLastName());
        if (req.getBirthDate() != null) s.setBirthDate(req.getBirthDate());
        if (req.getPhone() != null) s.setPhone(req.getPhone());
        if (req.getEmail() != null) s.setEmail(req.getEmail());
        if (req.getParentName() != null) s.setParentName(req.getParentName());
        if (req.getParentPhone() != null) s.setParentPhone(req.getParentPhone());
        return mapper.toStudentResponse(studentRepository.save(s));
    }

    public void deactivate(Long id) {
        Student s = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Учень", id));
        s.setStatus(Student.StudentStatus.INACTIVE);
        studentRepository.save(s);
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) throw new ResourceNotFoundException("Учень", id);
        studentRepository.deleteById(id);
    }
}
