package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("UserDto")
public class UserDto {
    private int id;
    private String nickname;
    private String email;
    private String password;
    private String profile_image;
    private int credit;
    private Timestamp subscriptionDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private int collegeId;
}
