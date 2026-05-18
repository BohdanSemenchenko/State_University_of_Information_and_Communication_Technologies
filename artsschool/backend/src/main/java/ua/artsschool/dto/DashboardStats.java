package ua.artsschool.dto;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardStats {
    public long totalStudents;
    public long activeStudents;
    public long totalTeachers;
    public long activeTeachers;
    public long totalGroups;
    public long activeGroups;
    public long fullGroups;
    public long underfilledGroups;
    public long formingGroups;
    public double avgFillRate;
    public List<GroupCapacityInfo> groupCapacities;
}
