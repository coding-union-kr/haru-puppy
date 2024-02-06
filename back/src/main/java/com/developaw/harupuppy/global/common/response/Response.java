package com.developaw.harupuppy.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class Response {
    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "가입된 유저가 아닙니다"),

        INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
        EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "요청 토큰이 만료되었습니다"),
        NOT_ACCESS_RESOURCE(HttpStatus.FORBIDDEN, "요청 리소스에 접근할 수 없습니다"),

        NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "해당 내용으로 저장된 스케줄이 없습니다"),

        BAD_REQUEST(HttpStatus.BAD_REQUEST, "비어 있는 곳이 있습니다"),

        NOT_FOUND_HOME(HttpStatus.NOT_FOUND, "해당 내용으로 저장된 스케줄이 없습니다"),

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
