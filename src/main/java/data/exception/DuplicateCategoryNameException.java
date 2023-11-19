package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateCategoryNameException extends RuntimeException {
    private final ErrorCode errorCode;
    public DuplicateCategoryNameException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
