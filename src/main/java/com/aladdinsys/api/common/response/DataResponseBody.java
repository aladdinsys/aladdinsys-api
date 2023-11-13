package com.aladdinsys.api.common.response;

import com.aladdinsys.api.common.constant.SuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS;

@Getter
public class DataResponseBody<T> extends ResponseBody {

    private final T result;

    public DataResponseBody(HttpStatus status, String message, T data) {
        super(status, message);
        this.result = data;
    }

    public static <T> DataResponseBody<T> of(T data) {
        return new DataResponseBody<>(SUCCESS.getHttpStatus(), SUCCESS.getMessage(), data);
    }

    public static <T> DataResponseBody<T> of(HttpStatus status, String message, T data) {
        return new DataResponseBody<>(status, message, data);
    }

    public static <C extends SuccessCode, T> DataResponseBody<T> of(@NotNull C code, T data) {
        return new DataResponseBody<>(code.getHttpStatus(), code.getMessage(), data);
    }

}
