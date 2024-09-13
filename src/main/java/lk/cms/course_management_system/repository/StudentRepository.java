package lk.cms.course_management_system.repository;

import lk.cms.course_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {
}
