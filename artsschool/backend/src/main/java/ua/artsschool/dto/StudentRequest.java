package ua.artsschool.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentRequest {
    @NotBlank(message = "Ім'я обов'язкове") @Size(min=2,max=50) public String firstName;
    @NotBlank(message = "Прізвище обов'язкове") @Size(min=2,max=50) public String lastName;
    @Past public LocalDate birthDate;
    public String phone;
    @Email public String email;
    public String parentName;
    public String parentPhone;
}
