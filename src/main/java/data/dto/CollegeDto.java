package data.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("CollegeDto")
public class CollegeDto {
    private int id;
    private String name;
    private Float latitude;
    private Float longitude;
}
