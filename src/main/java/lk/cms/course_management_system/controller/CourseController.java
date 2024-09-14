package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDto> saveStudent(@RequestBody CourseDto courseDto){
            CourseDto course = courseService.saveCourse(courseDto);
            return new ResponseEntity<>(course, HttpStatus.OK);
    }

}
