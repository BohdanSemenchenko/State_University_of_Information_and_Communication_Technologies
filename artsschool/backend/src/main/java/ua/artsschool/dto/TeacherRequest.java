package ua.artsschool.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import ua.artsschool.model.ArtDiscipline;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeacherRequest {
    @NotBlank public String firstName;
    @NotBlank public String lastName;
    public String phone;
    @Email public String email;
    public List<ArtDiscipline> disciplines;
}
