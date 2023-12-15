package com.developaw.harupuppy.global.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(Response.Status status, T data){
        return new ApiResponse<>(status.getStatus(), status.getMessage(), data);
    }
    public static <T> ApiResponse<T> ok(Response.Status status){
        return new ApiResponse<>(status.getStatus(), status.getMessage(), null);
    }
    public static <T> ApiResponse<T> error(Response.ErrorCode errorCode){
        return new ApiResponse<>(errorCode.getStatus(), errorCode.getMessage(), null);
    }
}
