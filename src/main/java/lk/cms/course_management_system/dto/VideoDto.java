package lk.cms.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

    private Integer id;
    private String name;
    private String videoFilePath;
    private String thumbnailFilePath;

}
