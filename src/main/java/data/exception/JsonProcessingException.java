package data.exception;

import data.constants.ErrorCode;
import io.jsonwebtoken.*;
import lombok.Getter;

@Getter
public class JsonProcessingException extends JwtException {
    private final ErrorCode errorCode;
    public JsonProcessingException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
