package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

    private Integer id;
    private String name;
    private String videoFilePath;
    private String thumbnailFilePath;
    private CourseDto course;

    public VideoDto(Integer id, String name, String videoFilePath, String thumbnailFilePath) {
        this.id = id;
        this.name = name;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }
}
