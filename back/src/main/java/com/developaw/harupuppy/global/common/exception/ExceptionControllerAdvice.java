package com.developaw.harupuppy.global.common.exception;

import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
