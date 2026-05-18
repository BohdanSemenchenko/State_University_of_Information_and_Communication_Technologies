package ua.artsschool.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.artsschool.dto.*;
import ua.artsschool.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Учні", description = "Реєстрація та управління учнями")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Отримати всіх учнів")
    public List<StudentResponse> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати учня за ID")
    public StudentResponse getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Пошук учнів за ім'ям або прізвищем")
    public List<StudentResponse> search(@RequestParam String q) {
        return studentService.search(q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Зареєструвати нового учня")
    public StudentResponse create(@Valid @RequestBody StudentRequest req) {
        return studentService.create(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані учня")
    public StudentResponse update(@PathVariable Long id, @RequestBody StudentRequest req) {
        return studentService.update(id, req);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Деактивувати учня")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        studentService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Видалити учня")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}
