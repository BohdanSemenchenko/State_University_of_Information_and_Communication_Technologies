package ua.artsschool.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnrollRequest {
    @NotNull public Long studentId;
    @NotNull public Long groupId;
    public String notes;
}
