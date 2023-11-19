package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final ErrorCode errorCode;
    public UnauthorizedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}