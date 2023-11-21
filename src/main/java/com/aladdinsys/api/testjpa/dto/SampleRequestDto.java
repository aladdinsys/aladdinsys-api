package com.aladdinsys.api.testjpa.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record SampleRequestDto(
        @NotNull(message = "년도는 필수 입니다.")
        String std_yy,
        @NotNull(message = "체육시설 대분류는 필수 입니다.")
        String phsrtn_fclty_lclas_nm,
        @NotNull(message = "체육시설 중분류는 필수 입니다.")
        String phsrtn_fclty_mclas_nm,
        @NotNull(message = "항목은 필수 입니다.")
        String iem_nm,
        @NotNull(message = "값은 필수 입니다.")
        String data_val
) {
    public SampleRequestDto {
        if (std_yy == null || phsrtn_fclty_lclas_nm == null || phsrtn_fclty_mclas_nm == null || iem_nm == null || data_val == null) {
            throw new IllegalArgumentException("must not be null");
        }
    }
}
