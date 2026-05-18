package ua.artsschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    private String phone;

    @Email
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_disciplines", joinColumns = @JoinColumn(name = "teacher_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<ArtDiscipline> disciplines = new ArrayList<>();

    @Builder.Default
    private boolean active = true;

    public String getFullName() { return lastName + " " + firstName; }
}
