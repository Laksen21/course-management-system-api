package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordChangeDto {
    private Integer id;
    private String name;
    private String status;
}
