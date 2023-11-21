package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;
    private final ErrorCode errorCode;

    public ValidationException(BindingResult bindingResult, ErrorCode errorCode) {
        this.bindingResult = bindingResult;
        this.errorCode = errorCode;
    }

    public ValidationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}