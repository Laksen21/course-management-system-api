package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.service.CourseService;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    JwtAuthenticator jwtAuthenticator;

    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto courseDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDto course = courseService.saveCourse(courseDto);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/course_with_video")
    public ResponseEntity<CourseDto> saveCourseWithVideo(@RequestBody CourseDto courseDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDto course = courseService.saveStudentWithSubject(courseDto);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<Object> getAllCourses(@RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            List<CourseDto> courses = courseService.getAllCourses();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

//    @GetMapping("/{CourseCode}")
//    public ResponseEntity<Object> getCourseById(@PathVariable String CourseCode) {
//
//    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable Integer courseId, @RequestBody CourseDto courseDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDto update = courseService.updateCourse(courseId,courseDto);
            if(update == null){
                return new ResponseEntity<>("No course found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/course_with_video/{courseId}")
    public ResponseEntity<Object> updateStudentWithCourse(@PathVariable Integer courseId, @RequestBody CourseDto courseDto, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDto update = courseService.updateCourseWithVideos(courseDto,courseId);
            if(update == null){
                return new ResponseEntity<>("No course found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId, @RequestHeader(name = "Authorization") String authHeader){
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            if(courseService.deleteCourse(courseId)){
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

}
