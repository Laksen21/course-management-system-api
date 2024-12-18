package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String username;
    private String password;

    public UserDto(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
