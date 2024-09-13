package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.dto.StudentDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.entity.Student;
import lk.cms.course_management_system.repository.CourseRepository;
import lk.cms.course_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<StudentDto> getAllStudent() {
        List<Student> allStudents = studentRepository.findAll();
        List<StudentDto> dtoList = new ArrayList<>();

        for (Student student : allStudents) {
            List<CourseDto> courseDtos = student.getCourses().stream()
                    .map(course -> new CourseDto(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            null  // Assuming CourseDto has a constructor matching these parameters
                    ))
                    .collect(Collectors.toList());
            dtoList.add(new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getTel_no(),student.getAddress(),student.getAppPassword(), courseDtos));
        }
        return dtoList;
    }

    public StudentDto saveStudentWithCourse(StudentDto studentDto) {

        //dto to entity
        ArrayList<Course> courses = new ArrayList<>();
        for (CourseDto courseDto : studentDto.getCourses()) {
            // Check if the course exists in the database
            Optional<Course> existingCourse = courseRepository.findById(courseDto.getId()); // <-- Added check for existing course

            if (existingCourse.isPresent()) {
                // Use the existing course
                courses.add(existingCourse.get()); // <-- Reuse the existing course if present
            } else {
                // Create a new course if it doesn't exist
                Course newCourse = new Course(courseDto.getId(), courseDto.getTitle(), courseDto.getDescription());
                courses.add(newCourse); // <-- Create and add a new course if it doesn't exist
            }
        }

        Student save = studentRepository.save(new Student(
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getTel_no(),
                studentDto.getAddress(),
                studentDto.getAppPassword(),
                courses));

        //entity to dto
        ArrayList<CourseDto> CourseDtos = new ArrayList<>();
        for (Course Course : save.getCourses()) {
            CourseDtos.add(new CourseDto(Course.getId(), Course.getTitle(), Course.getDescription()));
        }
        return new StudentDto(save.getId(), save.getName(), save.getEmail(), save.getTel_no(), save.getAddress(), save.getAppPassword(), CourseDtos);
    }


}
