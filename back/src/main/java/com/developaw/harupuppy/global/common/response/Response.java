package com.developaw.harupuppy.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class Response {
    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode{
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "가입된 유저가 아닙니다"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러")
        ;
        private final HttpStatus status;
        private final String message;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        UPDATE(HttpStatus.OK, "수정 되었습니다"),
        CREATE(HttpStatus.CREATED, "생성 되었습니다"),
        DELETE(HttpStatus.OK, "삭제 되었습니다"),
        RETRIEVE(HttpStatus.OK, "조회 되었습니다");
        private final HttpStatus status;
        private final String message;
    }
}
