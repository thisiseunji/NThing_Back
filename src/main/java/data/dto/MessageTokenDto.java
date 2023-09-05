package data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageTokenDto {
    @JsonProperty("message")
    private String message;
    @JsonProperty("token")
    private String token;
}
