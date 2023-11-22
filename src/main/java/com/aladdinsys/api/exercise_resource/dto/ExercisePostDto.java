package com.aladdinsys.api.exercise_resource.dto;

import javax.validation.constraints.NotNull;

public record ExercisePostDto(
        @NotNull(message = "년도는 필수 입니다.")
        String stdYy,

        @NotNull(message = "대분류는 필수 입니다.")
        String phsrtnFcltyLclasNm,

        @NotNull(message = "중분류는 필수 입니다.")
        String phsrtnFcltyMclasNm,

        @NotNull(message = "항목은 필수 입니다.")
        String iemNm,

        @NotNull(message = "값은 필수 입니다.")
        String dataVal
) {
        public ExercisePostDto {
                if (stdYy == null) {
                    throw new IllegalArgumentException("must not be null");
                }
                if (phsrtnFcltyLclasNm == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (phsrtnFcltyMclasNm == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (iemNm == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (dataVal == null) {
                        throw new IllegalArgumentException("must not be null");
                }
        }
}
