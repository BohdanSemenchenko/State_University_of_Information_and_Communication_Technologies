package ua.artsschool.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.artsschool.dto.*;
import ua.artsschool.service.TeacherService;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Викладачі", description = "Управління викладачами")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Отримати всіх викладачів")
    public List<TeacherResponse> getAll() { return teacherService.getAll(); }

    @GetMapping("/active")
    @Operation(summary = "Отримати активних викладачів")
    public List<TeacherResponse> getActive() { return teacherService.getActive(); }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати викладача за ID")
    public TeacherResponse getById(@PathVariable Long id) { return teacherService.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Додати викладача")
    public TeacherResponse create(@Valid @RequestBody TeacherRequest req) { return teacherService.create(req); }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані викладача")
    public TeacherResponse update(@PathVariable Long id, @RequestBody TeacherRequest req) {
        return teacherService.update(id, req);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Деактивувати викладача")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        teacherService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
