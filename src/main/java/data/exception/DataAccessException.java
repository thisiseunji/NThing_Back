package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class DataAccessException extends org.springframework.dao.DataAccessException {
    private final ErrorCode errorCode;
    public DataAccessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
