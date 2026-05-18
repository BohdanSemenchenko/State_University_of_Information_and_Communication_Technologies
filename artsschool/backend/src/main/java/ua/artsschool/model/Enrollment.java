package ua.artsschool.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Builder.Default
    private LocalDate enrollmentDate = LocalDate.now();
    private LocalDate withdrawalDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    private String notes;

    public enum EnrollmentStatus {
        ACTIVE("Активне"), COMPLETED("Завершено"), WITHDRAWN("Відраховано"), TRANSFERRED("Переведено");
        private final String displayName;
        EnrollmentStatus(String d) { this.displayName = d; }
        public String getDisplayName() { return displayName; }
    }
}
