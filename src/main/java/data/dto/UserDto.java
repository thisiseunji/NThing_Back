package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Data
public class UserDto {
    private int id;
    private String provider;
    private String providerId;
    private String nickname;
    private String email;
    private String password;
    private String profileImage;
    private int credit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp subscriptionDate;
    private int collegeId;
}
