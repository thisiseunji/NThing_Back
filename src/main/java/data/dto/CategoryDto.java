package data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("CategoryDto")
public class CategoryDto {
    private int id;
    private String name;
    private String image;

    @JsonIgnore
    private MultipartFile file;
}
