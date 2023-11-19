package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class NullPointerException extends java.lang.NullPointerException {
    private final ErrorCode errorCode;
    public NullPointerException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
