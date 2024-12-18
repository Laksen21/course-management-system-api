package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.VideoDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.entity.Video;
import lk.cms.course_management_system.repository.CourseRepository;
import lk.cms.course_management_system.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CourseRepository courseRepository;

    public VideoDto addVideo(Integer courseId, VideoDto videoDto) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            // Set the course ID to the video
            Video save = videoRepository.save(new Video(course, videoDto.getName()));
            return new VideoDto(save.getId(), save.getName(), save.getVideoFilePath(), save.getThumbnailFilePath());
        }
        return null;
    }

    public VideoDto updateVideo(Integer videoId, VideoDto videoDto) {
        Optional<Video> existVideo = videoRepository.findById(videoId);

        if (existVideo.isPresent()) {
            Video video = existVideo.get();

            Video save = videoRepository.save(new Video(video.getId(), videoDto.getName(),videoDto.getVideoFilePath(),videoDto.getThumbnailFilePath(), video.getCourse()));
            return new VideoDto(save.getId(), save.getName(), save.getVideoFilePath(), save.getThumbnailFilePath());
        }
        return null;
    }

    public boolean deleteVideo(Integer videoId) {
        if(videoRepository.existsById(videoId)){
            videoRepository.deleteById(videoId);
            return true;
        }
        return false;
    }

    public int fileUpload(MultipartFile video, MultipartFile thumbnail, Integer videoId) throws IOException {
        String videoFileName = video.getOriginalFilename();
        Path VideoUploadPath = Paths.get("uploads/videos", videoFileName);
        Files.createDirectories(VideoUploadPath.getParent());
        Files.write(VideoUploadPath,video.getBytes());

        String thumbnailFileName = thumbnail.getOriginalFilename();
        Path thumbnailUploadPath = Paths.get("uploads/thumbnails", thumbnailFileName);
        Files.createDirectories(thumbnailUploadPath.getParent());
        Files.write(thumbnailUploadPath,thumbnail.getBytes());

        Video Video = videoRepository.findById(videoId).get();
        Video.setVideoFilePath(VideoUploadPath.toString());
        Video.setThumbnailFilePath(thumbnailUploadPath.toString());
        Video save = videoRepository.save(Video);
        if (save != null) {
            return 1;
        }
        return 0;
    }

    public byte[] getThumbnail(Integer videoId) throws IOException {
        Video video = videoRepository.findById(videoId).get();
        Path path = Paths.get(video.getThumbnailFilePath());
        return Files.readAllBytes(path);
    }

    public Video getVideoById(Integer videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }
}
