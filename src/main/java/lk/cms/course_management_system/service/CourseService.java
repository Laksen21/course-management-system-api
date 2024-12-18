package lk.cms.course_management_system.service;

import lk.cms.course_management_system.dto.CourseDto;
import lk.cms.course_management_system.dto.VideoDto;
import lk.cms.course_management_system.entity.Course;
import lk.cms.course_management_system.entity.Student;
import lk.cms.course_management_system.entity.Video;
import lk.cms.course_management_system.repository.CourseRepository;
import lk.cms.course_management_system.repository.StudentRepository;
import lk.cms.course_management_system.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private StudentRepository studentRepository;

    public CourseDto saveCourse(CourseDto courseDto) {
        Course save = courseRepository.save(new Course(courseDto.getCode(),courseDto.getTitle(), courseDto.getDescription()));
        return new CourseDto(save.getId(), save.getCode(), save.getTitle(), save.getDescription());
    }

    public CourseDto saveStudentWithSubject(CourseDto courseDto) {
        //dto to entity
        ArrayList<Video> videos = new ArrayList<>();
        for (VideoDto videoDtos : courseDto.getVideos()) {
            videos.add(new Video(videoDtos.getName(), videoDtos.getVideoFilePath(), videoDtos.getThumbnailFilePath()));
        }

        Course save = courseRepository.save(new Course(courseDto.getCode(),courseDto.getTitle(), courseDto.getDescription(), videos));

        //entity to dto
        return getCourseDto(save);
    }

    public List<CourseDto> getAllCourses() {

        List<Course> allCourses = courseRepository.findAll();
        List<CourseDto> dtoList = new ArrayList<>();

        for (Course course : allCourses) {
            ArrayList<VideoDto> videoDtos = new ArrayList<>();
            for (Video videos : course.getVideos()) {
                videoDtos.add(new VideoDto(videos.getId(), videos.getName(), videos.getVideoFilePath(), videos.getThumbnailFilePath()));
            }
            dtoList.add(new CourseDto(course.getId(), course.getCode(), course.getTitle(), course.getDescription(), videoDtos));
        }
        return dtoList;
    }

//    public CourseDto getCourseByCode(Integer code) {
//
//    }

    public CourseDto updateCourse(Integer courseId, CourseDto courseDto) {

        Optional<Course> existCourse = courseRepository.findById(courseId);
        if (existCourse.isPresent()) {
            Course course = existCourse.get();

            course.setCode(courseDto.getCode());
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());

            Course update = courseRepository.save(course);

            return new CourseDto(update.getId(), update.getCode(), update.getTitle(), update.getDescription());
        }
        return null;
    }


    public CourseDto updateCourseWithVideos(CourseDto courseDto, Integer courseId) {

        ArrayList<Video> videos = new ArrayList<>();
        for (VideoDto videoDto : courseDto.getVideos()) {
            videos.add(new Video(videoDto.getId(), videoDto.getName(), videoDto.getVideoFilePath(), videoDto.getThumbnailFilePath()));
        }

        if (courseRepository.existsById(courseId)) {
            Course update = courseRepository.save(new Course(courseId, courseDto.getCode(),courseDto.getTitle(),courseDto.getDescription(), videos));

            return getVideoDto(update);
        }
        return null;
    }


    public boolean deleteCourse(Integer courseId) {
        Optional<Course> existCourse = courseRepository.findById(courseId);
        if(existCourse.isPresent()){
            Course course = existCourse.get();
            for (Student student : course.getStudents()) {
                student.getCourses().remove(course);
                studentRepository.save(student);
            }
            courseRepository.deleteById(courseId);
            return true;
        }
        return false;
    }

    private CourseDto getVideoDto(Course course) {

        return getCourseDto(course);
    }

    private CourseDto getCourseDto(Course course) {
        ArrayList<VideoDto> videoDtos = new ArrayList<>();
        for (Video video : course.getVideos()) {
            videoDtos.add(new VideoDto(video.getId(), video.getName(), video.getVideoFilePath(), video.getThumbnailFilePath()));
        }

        return new CourseDto(course.getId(), course.getCode(), course.getTitle(), course.getDescription(),videoDtos);
    }

    public CourseDto getCourseById(Integer courseId) {
        if (courseRepository.existsById(courseId)) {
            Course course = courseRepository.findById(courseId).get();
            return getVideoDto(course);
        }
       return null;
    }
}
