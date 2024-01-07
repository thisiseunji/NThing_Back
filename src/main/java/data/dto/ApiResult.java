package data.dto;

import data.constants.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResult<T> {
    @Schema(description = "응답 데이터")
    private final T data;
    @Schema(description = "메시지(성공 시 null)")
    private final String message;
    @Schema(description = "응답 상태")
    private final int status;
    @Schema(description = "에러 코드(성공 시 null)")
    private final String code;

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(data, null, 200, null);
    }

    public static <T> ApiResult<T> created(T data) {
        return new ApiResult<>(data, null, 201, null);
    }

    public static ApiResult<?> created() {
        return new ApiResult<>(null, null, 201, null);
    }

    public static ApiResult<?> noContent() {
        return new ApiResult<>(null, null, 204, null);
    }

    public static ApiResult<?> error(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode);
        return new ApiResult<>(null, response.getMessage(), response.getStatus(), response.getCode());
    }
}
