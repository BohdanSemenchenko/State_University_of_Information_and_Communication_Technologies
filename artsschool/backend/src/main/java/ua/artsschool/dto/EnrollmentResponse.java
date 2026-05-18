package ua.artsschool.dto;
import lombok.*;
import ua.artsschool.model.Enrollment;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnrollmentResponse {
    public Long id;
    public StudentResponse student;
    public Long groupId;
    public String groupName;
    public LocalDate enrollmentDate;
    public LocalDate withdrawalDate;
    public Enrollment.EnrollmentStatus status;
    public String notes;
}
