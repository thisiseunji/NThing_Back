package data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageTokenDto {
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Data data;

    public  MessageTokenDto(String message, String accessToken, String refreshToken) {
        this.message = message;
        this.data = new Data(accessToken, refreshToken);
    }

    @AllArgsConstructor
    public class Data {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;
    }
}
