package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Alias("UserDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
public class UserDto {
    private int id;
    private String nickname;
    private String email;
    private String profileImage;
    private int credit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp subscriptionDate;
    private int collegeId;
    private String providerId;
    private String provider;
}
