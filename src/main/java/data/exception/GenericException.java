package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class GenericException extends Exception {
    private final ErrorCode errorCode;
    public GenericException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
