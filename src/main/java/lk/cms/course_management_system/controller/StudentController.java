package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.StudentDto;
import lk.cms.course_management_system.entity.Student;
import lk.cms.course_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student_with_course")
    public ResponseEntity<StudentDto> saveStudentWithSubject(@RequestBody StudentDto studentDto) {
        StudentDto savedStudent = studentService.saveStudentWithCourse(studentDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.OK);
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudent();
    }
}
