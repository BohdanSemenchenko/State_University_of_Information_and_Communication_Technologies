package ua.artsschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.artsschool.model.Student;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStatus(Student.StudentStatus status);

    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%',:q,'%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%',:q,'%'))")
    List<Student> searchByName(String q);

    long countByStatus(Student.StudentStatus status);
}
