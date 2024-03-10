package data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import data.constants.ErrorCode;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    @Schema(description = "응답 데이터(에러 발생 시 null)")
    private String data;
    @Schema(description = "에러 메시지")
    private String message;
    @Schema(description = "응답 상태")
    private int status;
    @Schema(description = "에러 코드")
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Hidden
    private List<String> errors = null;

    public ErrorResponse(ErrorCode errorCode) {
        this.data = null;
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}