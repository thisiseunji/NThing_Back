package data.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("CollegeDto")
public class CollegeDto {
    private int id;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
}
