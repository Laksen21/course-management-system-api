package lk.cms.course_management_system.repository;

import lk.cms.course_management_system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course getCourseByCode(String code);
}
