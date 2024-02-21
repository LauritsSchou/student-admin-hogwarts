package edu.hogwarts.student.repository;

import edu.hogwarts.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE lower(concat(s.firstName, ' ', s.middleName, ' ', s.lastName)) LIKE lower(concat('%', :name, '%'))")
    List<Student> findByFullNameContainingIgnoreCase(@Param("name") String name);
}
