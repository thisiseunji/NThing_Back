package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class DeletedCommentException extends RuntimeException {
    private final ErrorCode errorCode;
    public DeletedCommentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}