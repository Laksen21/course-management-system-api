package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Integer id;
    private String title;
    private String description;
    private List<StudentDto> students;

    public CourseDto(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
