package data.exception;

import data.constants.ErrorCode;
import lombok.Getter;

@Getter
public class PurchaseNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public PurchaseNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}