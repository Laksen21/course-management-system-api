package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.VideoDto;
import lk.cms.course_management_system.service.VideoService;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    JwtAuthenticator jwtAuthenticator;

    @PostMapping("/add/{courseId}")
    public ResponseEntity<VideoDto> saveVideo(@PathVariable Integer courseId, @RequestBody VideoDto videoDto, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            VideoDto savedVideo = videoService.addVideo(courseId, videoDto);
            return new ResponseEntity<>(savedVideo, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/update/{videoId}")
    public ResponseEntity<Object> updateVideo(@PathVariable Integer videoId, @RequestBody VideoDto videoDto, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            VideoDto update = videoService.updateVideo(videoId, videoDto);
            if(update == null){
                return new ResponseEntity<>("No video found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<Object> deleteVideo(@PathVariable Integer videoId, @RequestHeader(name = "Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            if(videoService.deleteVideo(videoId)){
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("No data found !", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
