package ua.artsschool.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransferRequest {
    @NotNull public Long studentId;
    @NotNull public Long fromGroupId;
    @NotNull public Long toGroupId;
}
