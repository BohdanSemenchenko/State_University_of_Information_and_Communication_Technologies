package ua.artsschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.artsschool.model.ArtDiscipline;
import ua.artsschool.model.Group;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByDiscipline(ArtDiscipline discipline);
    List<Group> findByStatus(Group.GroupStatus status);
    List<Group> findByTeacherId(Long teacherId);
    long countByStatus(Group.GroupStatus status);

    @Query("SELECT g FROM Group g WHERE g.status <> 'CLOSED' AND g.status <> 'SUSPENDED' AND SIZE(g.students) < g.maxCapacity")
    List<Group> findAvailable();
}
