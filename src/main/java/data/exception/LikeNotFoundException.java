package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class LikeNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public LikeNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}