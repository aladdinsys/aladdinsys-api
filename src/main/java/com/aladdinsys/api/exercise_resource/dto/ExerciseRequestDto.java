package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record ExerciseRequestDto(
        @NotNull(message = "년도는 필수 입니다.")
        String stdYy,
        @NotNull(message = "체육시설 대분류는 필수 입니다.")
        String phsrtnFcltyLclasNm,
        @NotNull(message = "체육시설 중분류는 필수 입니다.")
        String phsrtnFcltyMclasNm,
        @NotNull(message = "항목은 필수 입니다.")
        String iemNm,
        @NotNull(message = "값은 필수 입니다.")
        String dataVal
) {
    public ExerciseRequestDto {
        if (stdYy == null || phsrtnFcltyLclasNm == null || phsrtnFcltyMclasNm == null || iemNm == null || dataVal == null) {
            throw new IllegalArgumentException("must not be null");
        }
    }
}
