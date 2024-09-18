package lk.cms.course_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String code;
    private String title;
    private String description;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Video> videos;

    public Course(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public Course(String code, String title, String description, List<Video> videos) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.videos = videos;
    }

    public Course(Integer id, String code, String title, String description) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public Course(Integer id, String code, String title, String description, List<Video> videos) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.videos = videos;
    }
}
