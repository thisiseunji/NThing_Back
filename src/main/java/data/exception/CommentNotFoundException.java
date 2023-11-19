package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public CommentNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}