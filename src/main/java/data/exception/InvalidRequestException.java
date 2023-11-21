package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final ErrorCode errorCode;
    public InvalidRequestException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}