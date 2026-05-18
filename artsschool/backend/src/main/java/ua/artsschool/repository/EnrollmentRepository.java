package ua.artsschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.artsschool.model.Enrollment;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByGroupId(Long groupId);
    Optional<Enrollment> findByStudentIdAndGroupIdAndStatus(Long studentId, Long groupId, Enrollment.EnrollmentStatus status);
    boolean existsByStudentIdAndGroupIdAndStatus(Long studentId, Long groupId, Enrollment.EnrollmentStatus status);
}
