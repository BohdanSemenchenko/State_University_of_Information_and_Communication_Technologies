package ua.artsschool.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupCapacityInfo {
    public Long id;
    public String name;
    public String discipline;
    public String disciplineKey;
    public int currentSize;
    public int maxCapacity;
    public int minCapacity;
    public double fillRate;
    public String status;
    public String statusDisplayName;
}
