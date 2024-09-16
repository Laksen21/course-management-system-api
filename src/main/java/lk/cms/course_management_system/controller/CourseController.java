package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.service.CourseService;
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

    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto courseDto){
            CourseDto course = courseService.saveCourse(courseDto);
            return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("/course_with_video")
    public ResponseEntity<CourseDto> saveCourseWithVideo(@RequestBody CourseDto courseDto){
        CourseDto course = courseService.saveStudentWithSubject(courseDto);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping
    public List<CourseDto> getAllCourses(){
        return courseService.getAllCourses();
    }

//    @GetMapping("/{CourseCode}")
//    public ResponseEntity<Object> getCourseById(@PathVariable String CourseCode) {
//
//    }

    @PutMapping("/course_with_video/{courseId}")
    public ResponseEntity<Object> updateStudentWithCourse(@PathVariable Integer courseId, @RequestBody CourseDto courseDto){
        CourseDto update = courseService.updateCourseWithVideos(courseDto,courseId);
        if(update == null){
            return new ResponseEntity<>("No student found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId){
        if(courseService.deleteCourse(courseId)){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
    }

}
