package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetDto {
    private String currentPassword;
    private String newPassword;
}
