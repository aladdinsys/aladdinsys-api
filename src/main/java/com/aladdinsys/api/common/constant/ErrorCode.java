package com.aladdinsys.api.common.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode implements Code {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 데이터를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getStatusName() {
        return this.httpStatus.name();
    }

    @Override
    public Integer getStatusValue() {
        return this.httpStatus.value();
    }
}
