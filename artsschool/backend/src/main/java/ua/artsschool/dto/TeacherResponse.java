package ua.artsschool.dto;
import lombok.*;
import ua.artsschool.model.ArtDiscipline;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeacherResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String fullName;
    public String phone;
    public String email;
    public List<ArtDiscipline> disciplines;
    public boolean active;
}
