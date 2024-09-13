package lk.cms.course_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer id;
    private String title;
    private String description;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Student> students;

    public Course(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
