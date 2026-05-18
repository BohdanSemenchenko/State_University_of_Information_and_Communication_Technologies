package ua.artsschool.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import ua.artsschool.model.ArtDiscipline;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupRequest {
    @NotBlank public String name;
    @NotNull public ArtDiscipline discipline;
    public Long teacherId;
    @Min(1) @Builder.Default public int minCapacity = 4;
    @Min(1) @Builder.Default public int maxCapacity = 12;
    public String schedule;
    public String ageRange;
    public String classroom;
}
