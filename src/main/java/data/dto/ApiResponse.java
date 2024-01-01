package data.dto;

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
}
