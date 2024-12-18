package lk.cms.course_management_system.dto;

import lk.cms.course_management_system.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Integer id;
    private String name;
    private String email;
    private String tel_no;
    private String address;
    private String appPassword;
    private List<CourseDto> courses;

    public StudentDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
