package ua.artsschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ім'я є обов'язковим")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Прізвище є обов'язковим")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String lastName;

    @Past(message = "Дата народження має бути в минулому")
    private LocalDate birthDate;

    @Pattern(regexp = "\\+?[0-9\\s\\-()]{7,20}", message = "Невірний формат телефону")
    private String phone;

    @Email(message = "Невірний формат email")
    private String email;

    private String parentName;
    private String parentPhone;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StudentStatus status = StudentStatus.ACTIVE;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    public String getFullName() { return lastName + " " + firstName; }

    public enum StudentStatus {
        ACTIVE("Активний"), INACTIVE("Неактивний"), SUSPENDED("Призупинений");
        private final String displayName;
        StudentStatus(String d) { this.displayName = d; }
        public String getDisplayName() { return displayName; }
    }
}
