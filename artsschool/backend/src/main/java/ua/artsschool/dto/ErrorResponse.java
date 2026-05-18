package ua.artsschool.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ErrorResponse {
    public int status;
    public String error;
    public String message;
    public LocalDateTime timestamp;
}
