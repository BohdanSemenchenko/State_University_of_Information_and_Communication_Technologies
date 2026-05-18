package ua.artsschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ArtDiscipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Min(1) @Column(nullable = false)
    @Builder.Default private int minCapacity = 4;

    @Min(1) @Column(nullable = false)
    @Builder.Default private int maxCapacity = 12;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_students",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private GroupStatus status = GroupStatus.FORMING;

    private String schedule;
    private String ageRange;
    private String classroom;

    // ── Capacity logic ─────────────────────────────────────────────────────
    public int getCurrentSize() { return students != null ? students.size() : 0; }
    public int getAvailableSlots() { return Math.max(0, maxCapacity - getCurrentSize()); }
    public double getFillRate() { return maxCapacity > 0 ? (double) getCurrentSize() / maxCapacity * 100.0 : 0; }
    public boolean isFull() { return getCurrentSize() >= maxCapacity; }
    public boolean isUnderfilled() { return getCurrentSize() < minCapacity && getCurrentSize() > 0; }
    public boolean canEnroll() { return !isFull() && status != GroupStatus.CLOSED && status != GroupStatus.SUSPENDED; }

    public boolean autoUpdateStatus() {
        if (status == GroupStatus.CLOSED || status == GroupStatus.SUSPENDED) return false;
        GroupStatus next;
        int sz = getCurrentSize();
        if (sz >= maxCapacity) next = GroupStatus.FULL;
        else if (sz < minCapacity && sz > 0) next = GroupStatus.UNDERFILLED;
        else if (sz == 0) next = GroupStatus.FORMING;
        else next = GroupStatus.ACTIVE;
        if (next != status) { status = next; return true; }
        return false;
    }

    public enum GroupStatus {
        FORMING("Набір"), ACTIVE("Активна"), FULL("Заповнена"),
        UNDERFILLED("Недоукомплектована"), SUSPENDED("Призупинена"), CLOSED("Закрита");
        private final String displayName;
        GroupStatus(String d) { this.displayName = d; }
        public String getDisplayName() { return displayName; }
    }
}
