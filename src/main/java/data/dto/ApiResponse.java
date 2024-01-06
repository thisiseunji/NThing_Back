package data.dto;

import data.constants.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final T data;
    private final String message;
    private final int status;
    private final String code;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, null, 200, null);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(data, null, 201, null);
    }

    public static ApiResponse<?> created() {
        return new ApiResponse<>(null, null, 201, null);
    }

    public static ApiResponse<?> noContent() {
        return new ApiResponse<>(null, null, 204, null);
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode);
        return new ApiResponse<>(null, response.getMessage(), response.getStatus(), response.getCode());
    }
}
