package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.ibatis.type.Alias;

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
    private String provider;
    private String providerId;
    private String nickname;
    private String email;
    private String profileImage;
    private int credit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp subscriptionDate;
    private int collegeId;
}
