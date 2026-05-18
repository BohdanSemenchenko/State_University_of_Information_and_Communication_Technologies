package ua.artsschool.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.artsschool.dto.*;
import ua.artsschool.model.*;
import ua.artsschool.service.GroupService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Групи", description = "Управління навчальними групами та контроль наповненості")
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Отримати всі групи")
    public List<GroupResponse> getAll() { return groupService.getAll(); }

    @GetMapping("/available")
    @Operation(summary = "Отримати групи доступні для запису")
    public List<GroupResponse> getAvailable() { return groupService.getAvailable(); }

    @GetMapping("/{id}")
    @Operation(summary = "Деталі групи зі списком учнів")
    public GroupResponse getById(@PathVariable Long id) { return groupService.getById(id); }

    @GetMapping("/discipline/{discipline}")
    @Operation(summary = "Групи за дисципліною")
    public List<GroupResponse> getByDiscipline(@PathVariable ArtDiscipline discipline) {
        return groupService.getByDiscipline(discipline);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Створити нову групу")
    public GroupResponse create(@Valid @RequestBody GroupRequest req) { return groupService.create(req); }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані групи")
    public GroupResponse update(@PathVariable Long id, @RequestBody GroupRequest req) {
        return groupService.update(id, req);
    }

    @PatchMapping("/{id}/capacity")
    @Operation(summary = "Оновити ліміти наповненості групи")
    public ResponseEntity<Void> updateCapacity(@PathVariable Long id,
                                               @RequestParam int min,
                                               @RequestParam int max) {
        groupService.updateCapacity(id, min, max);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Змінити статус групи (SUSPENDED, ACTIVE, CLOSED)")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id,
                                             @RequestBody Map<String, String> body) {
        Group.GroupStatus status = Group.GroupStatus.valueOf(body.get("status"));
        groupService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    // ── Enrollment endpoints ─────────────────────────────────────────────────

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Зарахувати учня до групи")
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollRequest req) {
        return groupService.enroll(req.getStudentId(), req.getGroupId(), req.getNotes());
    }

    @DeleteMapping("/{groupId}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Відрахувати учня з групи")
    public void withdraw(@PathVariable Long groupId, @PathVariable Long studentId) {
        groupService.withdraw(studentId, groupId);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Перевести учня між групами")
    public EnrollmentResponse transfer(@Valid @RequestBody TransferRequest req) {
        return groupService.transfer(req.getStudentId(), req.getFromGroupId(), req.getToGroupId());
    }

    @GetMapping("/{id}/enrollments")
    @Operation(summary = "Журнал зарахувань групи")
    public List<EnrollmentResponse> getGroupEnrollments(@PathVariable Long id) {
        return groupService.getEnrollmentsByGroup(id);
    }

    @GetMapping("/students/{studentId}/enrollments")
    @Operation(summary = "Зарахування конкретного учня")
    public List<EnrollmentResponse> getStudentEnrollments(@PathVariable Long studentId) {
        return groupService.getEnrollmentsByStudent(studentId);
    }
}
