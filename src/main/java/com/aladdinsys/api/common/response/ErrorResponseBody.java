package com.aladdinsys.api.common.response;

import com.aladdinsys.api.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

@Getter
public class ErrorResponseBody extends ResponseBody {

    private final String code;
    private final String error;

    public ErrorResponseBody(HttpStatus status, String message, String code, String error) {
        super(status, message);
        this.code = code;
        this.error = error;
    }

    public static <T extends ErrorCode> ErrorResponseBody of(@NotNull T errorCode) {
        return new ErrorResponseBody(errorCode.getHttpStatus(), errorCode.getMessage(), errorCode.name(), errorCode.getStatusName());
    }

    public static <T extends ErrorCode> ErrorResponseBody of(HttpStatus status, String message, @NotNull T errorCode) {
        return new ErrorResponseBody(status, message, errorCode.name(), errorCode.getStatusName());
    }

    public static ErrorResponseBody of(HttpStatus status, String message) {
        return new ErrorResponseBody(status, message, String.valueOf(status.value()), "NullPointerException");
    }
}
