package data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IdToken {
    @JsonProperty("id_token")
    private String idToken;
}
