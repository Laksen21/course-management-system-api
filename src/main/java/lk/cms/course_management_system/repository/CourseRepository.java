package lk.cms.course_management_system.repository;

import lk.cms.course_management_system.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {
}
