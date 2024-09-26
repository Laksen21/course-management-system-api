package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private Integer id;
    private String username;
    private String msg;
    private String token;

}
