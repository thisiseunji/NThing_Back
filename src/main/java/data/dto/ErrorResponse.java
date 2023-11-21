package data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import data.constants.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().toString();
    private int status;
    private String message;
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors = null;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}