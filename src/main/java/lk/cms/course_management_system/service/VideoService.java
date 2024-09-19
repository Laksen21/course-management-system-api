package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.VideoDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.entity.Video;
import lk.cms.course_management_system.repository.CourseRepository;
import lk.cms.course_management_system.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Video save = videoRepository.save(new Video(course, videoDto.getName(),videoDto.getVideoFilePath(),videoDto.getThumbnailFilePath()));// Save the video separately
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
}
