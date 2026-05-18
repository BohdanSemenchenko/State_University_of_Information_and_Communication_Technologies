package ua.artsschool.dto;
import lombok.*;
import ua.artsschool.model.ArtDiscipline;
import ua.artsschool.model.Group;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupResponse {
    public Long id;
    public String name;
    public ArtDiscipline discipline;
    public String disciplineDisplayName;
    public TeacherResponse teacher;
    public int minCapacity;
    public int maxCapacity;
    public int currentSize;
    public int availableSlots;
    public double fillRate;
    public Group.GroupStatus status;
    public String statusDisplayName;
    public String schedule;
    public String ageRange;
    public String classroom;
    public List<StudentResponse> students;
}
