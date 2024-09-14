package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseDto saveCourse(CourseDto courseDto) {
        Course save = courseRepository.save(new Course(courseDto.getId(),courseDto.getTitle(), courseDto.getDescription()));
        return new CourseDto(save.getId(), save.getTitle(), save.getDescription());
    }
}
