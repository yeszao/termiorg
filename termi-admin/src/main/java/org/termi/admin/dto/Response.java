package org.termi.admin.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Response<T> success() {
        return success(HttpStatus.OK.value(), null);
    }

    public static <T> Response<T> success(T data) {
        return success(HttpStatus.OK.value(), data);
    }

    public static <T> Response<T> success(int code, T data) {
        return Response.<T>builder()
                .code(code)
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    public static <T> Response<T> error(int code, String message) {
        return Response.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}