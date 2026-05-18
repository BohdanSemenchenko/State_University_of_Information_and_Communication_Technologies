package ua.artsschool.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.artsschool.dto.DashboardStats;
import ua.artsschool.model.ArtDiscipline;
import ua.artsschool.service.GroupService;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Моніторинг", description = "Панель моніторингу наповненості та статистика")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Загальна статистика системи")
    public DashboardStats getDashboard() {
        return groupService.getDashboard();
    }

    @GetMapping("/disciplines")
    @Operation(summary = "Перелік доступних дисциплін")
    public List<Map<String, String>> getDisciplines() {
        return Arrays.stream(ArtDiscipline.values())
                .map(d -> Map.of("key", d.name(), "name", d.getDisplayName()))
                .collect(Collectors.toList());
    }
}
