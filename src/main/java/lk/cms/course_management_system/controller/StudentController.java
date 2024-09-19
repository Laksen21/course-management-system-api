package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.LoginResponseDto;
import lk.cms.course_management_system.dto.StudentDto;
import lk.cms.course_management_system.service.StudentService;
import lk.cms.course_management_system.util.JwtAuthenticator;
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

    @Autowired
    JwtAuthenticator jwtAuthenticator;

    @PostMapping("/student_with_course")
    public ResponseEntity<StudentDto> saveStudentWithSubject(@RequestBody StudentDto studentDto, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            StudentDto savedStudent = studentService.saveStudentWithCourse(studentDto);
            return new ResponseEntity<>(savedStudent, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<Object> getAllStudents(@RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            List<StudentDto> students = studentService.getAllStudent();
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{StudentId}")
    public ResponseEntity<Object> getStudentById(@PathVariable Integer StudentId, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            StudentDto studentDto = studentService.getStudentById(StudentId);
            if(studentDto == null){
                return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/student_with_course/{studentId}")
    public ResponseEntity<Object> updateStudentWithCourse(@PathVariable Integer studentId, @RequestBody StudentDto studentDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            StudentDto update = studentService.updateStudentWithCourse(studentId,studentDto);
            if(update == null){
                return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            if(studentService.deleteStudent(studentId)){
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    //student app login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> StudentLogin(@RequestBody StudentDto studentDto) {
        LoginResponseDto login = studentService.studentLogin(studentDto);
        if (login.getToken() == null) {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

}
