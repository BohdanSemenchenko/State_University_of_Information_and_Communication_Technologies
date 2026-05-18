package ua.artsschool.dto;
import lombok.*;
import ua.artsschool.model.Student;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String fullName;
    public LocalDate birthDate;
    public String phone;
    public String email;
    public String parentName;
    public String parentPhone;
    public Student.StudentStatus status;
    public LocalDate createdAt;
}
