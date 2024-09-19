package lk.cms.course_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lk.cms.course_management_system.dto.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String videoFilePath;
    private String thumbnailFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Video(String name, String videoFilePath, String thumbnailFilePath) {
        this.name = name;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Video(Integer id, String name, String videoFilePath, String thumbnailFilePath) {
        this.id = id;
        this.name = name;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Video(Course course, String name, String videoFilePath, String thumbnailFilePath) {
        this.course = course;
        this.name = name;
        this.videoFilePath = videoFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
    }


}
