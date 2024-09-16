package lk.cms.course_management_system.dto;

import lk.cms.course_management_system.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Integer id;
    private String code;
    private String title;
    private String description;
    private List<StudentDto> students;
    private List<VideoDto> videos;

    public CourseDto(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public CourseDto(Integer id, String code, String title, String description) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public CourseDto(Integer id, String code, String title, String description, List<VideoDto> videos) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.videos = videos;
    }
}
