package lk.cms.course_management_system.entity;

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
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String tel_no;
    private String address;
    private String appPassword;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "student_course",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id")
            }
    )
    @JsonManagedReference
    private List<Course> courses;

    public Student(String name, String email, String tel_no, String address, String appPassword, List<Course> courses) {
        this.name = name;
        this.email = email;
        this.tel_no = tel_no;
        this.address = address;
        this.appPassword = appPassword;
        this.courses = courses;
    }
}
