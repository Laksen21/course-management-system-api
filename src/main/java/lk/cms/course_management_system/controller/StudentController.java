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

    @GetMapping("/{Studentid}")
    public ResponseEntity<Object> getStudentById(@PathVariable Integer Studentid) {
        StudentDto studentDto = studentService.getStudentById(Studentid);
        if(studentDto == null){
            return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PutMapping("/student_with_course/{studentId}")
    public ResponseEntity<Object> updateStudentWithCourse(@PathVariable Integer studentId, @RequestBody StudentDto studentDto){
        StudentDto update = studentService.updateStudentWithCourse(studentId,studentDto);
        if(update == null){
            return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId){
        if(studentService.deleteStudent(studentId)){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
    }
}
