package lk.cms.course_management_system.controller;

import lk.cms.course_management_system.dto.VideoDto;
import lk.cms.course_management_system.entity.Video;
import lk.cms.course_management_system.service.VideoService;
import lk.cms.course_management_system.util.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/file-upload/{videoId}")
    public ResponseEntity<String> uploadFiles(@RequestParam("video") MultipartFile video, @RequestParam("thumbnail") MultipartFile thumbnail, @PathVariable Integer videoId) throws IOException {
        int i = videoService.fileUpload(video, thumbnail, videoId);
        if(i==1){
            return new ResponseEntity<String>("Upload Success !",HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Upload Failed !",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<ResourceRegion> streamVideo(@PathVariable Integer videoId, @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        Video video = videoService.getVideoById(videoId);
        Resource videoResource = new FileSystemResource(video.getVideoFilePath());
        ResourceRegion region = resourceRegion(videoResource, rangeHeader);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);
    }

    private ResourceRegion resourceRegion(Resource video, String rangeHeader) throws IOException {
        long contentLength = video.contentLength();
        int chunkSize = 10000000; // 1MB chunk
        if (rangeHeader == null) {
            return new ResourceRegion(video, 0, Math.min(chunkSize, contentLength));
        }
        String[] ranges = rangeHeader.replace("bytes=", "").split("-");
        long start = Long.parseLong(ranges[0]);
        long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : contentLength - 1;
        long rangeLength = Math.min(chunkSize, end - start + 1);
        return new ResourceRegion(video, start, rangeLength);
    }

    @GetMapping("/thumbnail/{videoId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer videoId) throws IOException {
        byte[] image = videoService.getThumbnail(videoId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image,headers,HttpStatus.OK);
    }
}
