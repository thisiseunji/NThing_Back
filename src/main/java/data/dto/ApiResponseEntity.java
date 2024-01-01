package data.dto;

import lombok.Getter;

@Getter
public class ApiResponseEntity<T> {

    private final T data;
    private final String message;
    private final int status;
    private final String code;

    private ApiResponseEntity(T data, String message, int status, String code) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public static <T> ApiResponseEntity<T> ok(T data) {
        if (data != null) {
            return new ApiResponseEntity<>(data, null, 200, null);
        } else {
            return noContent();
        }
    }

    public static <T> ApiResponseEntity<T> created(T data) {
        return new ApiResponseEntity<>(data, null, 201, null);
    }

    public static <T> ApiResponseEntity<T> created() {
        return created(null);
    }

    public static <T> ApiResponseEntity<T> noContent() {
        return new ApiResponseEntity<>(null, null, 204, null);
    }

    public static <T> ApiResponseEntity<T> custom(T data, String message, int status, String code) {
        return new ApiResponseEntity<>(data, message, status, code);
    }
}
