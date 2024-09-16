package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.dto.LoginResponseDto;
import lk.cms.course_management_system.dto.StudentDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.entity.Student;
import lk.cms.course_management_system.repository.CourseRepository;
import lk.cms.course_management_system.repository.StudentRepository;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    JwtAuthenticator jwtAuthenticator;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public StudentDto saveStudentWithCourse(StudentDto studentDto) {

        //dto to entity
        ArrayList<Course> courses = new ArrayList<>();
        for (CourseDto courseDto : studentDto.getCourses()) {
            Optional<Course> existingCourse = Optional.ofNullable(courseRepository.getCourseByCode(courseDto.getCode())); // check course existence
            if (existingCourse.isPresent()) {
                // Use the existing course
                courses.add(existingCourse.get()); // <-- Reuse the existing course if present
            } else {
                // Create a new course if it doesn't exist
                Course newCourse = new Course(courseDto.getCode(), courseDto.getTitle(), courseDto.getDescription());
                courses.add(newCourse); // create new course if it doesn't exist
            }
        }

        String encodedPassword = passwordEncoder.encode(studentDto.getAppPassword());

        Student save = studentRepository.save(new Student(
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getTel_no(),
                studentDto.getAddress(),
                encodedPassword,
                courses));

        return getCourseDto(save);
    }

    public List<StudentDto> getAllStudent() {

        List<Student> allStudents = studentRepository.findAll();
        List<StudentDto> dtoList = new ArrayList<>();

        for (Student student : allStudents) {
            ArrayList<CourseDto> courseDtos = new ArrayList<>();
            for (Course course : student.getCourses()) {
                courseDtos.add(new CourseDto(course.getId(), course.getCode(),course.getTitle(),course.getDescription()));
            }
            dtoList.add(new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getTel_no(),student.getAddress(),student.getAppPassword(), courseDtos));
        }
        return dtoList;
    }

    public StudentDto getStudentById(Integer id) {

        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();

            return getCourseDto(student);
        }
        return null;
    }

    public StudentDto updateStudentWithCourse(Integer studentId, StudentDto studentDto) {

        ArrayList<Course> courses = new ArrayList<>();
        for (CourseDto courseDto : studentDto.getCourses()) {
            courses.add(new Course(courseDto.getCode(),courseDto.getTitle(),courseDto.getDescription()));
        }

        String encodedPassword = passwordEncoder.encode(studentDto.getAppPassword());

        if (studentRepository.existsById(studentId)) {
            Student update = studentRepository.save(new Student(studentId, studentDto.getName(), studentDto.getEmail(), studentDto.getTel_no(),studentDto.getAddress(),encodedPassword,courses));

            return getCourseDto(update);
        }
        return null;
    }

    public boolean deleteStudent(Integer studentId) {

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            for (Course course : student.getCourses()) {
                course.getStudents().remove(student);
                courseRepository.save(course);
            }
            studentRepository.deleteById(studentId);
            return true;
        }
        return false;
    }

    public LoginResponseDto studentLogin(StudentDto studentDto) {

        Student studentByEmail = studentRepository.findStudentByEmail(studentDto.getEmail());
        if (passwordEncoder.matches(studentDto.getAppPassword(), studentByEmail.getAppPassword())) {
            String jwtToken = jwtAuthenticator.generateJwtToken(studentByEmail);
            return new LoginResponseDto(studentByEmail.getEmail(), "Login Success !", jwtToken);
        }
        return new LoginResponseDto(studentByEmail.getEmail(), "Login Failed !", null);
    }

    private StudentDto getCourseDto(Student student) {

        ArrayList<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : student.getCourses()) {
            courseDtos.add(new CourseDto(course.getCode(),course.getTitle(),course.getDescription()));
        }

        return new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getTel_no(),student.getAddress(),student.getAppPassword(),courseDtos);
    }

}
