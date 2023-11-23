package com.aladdinsys.api.exercise_resource.dto;

import javax.validation.constraints.NotNull;

public record ExercisePostDto(
        @NotNull(message = "년도는 필수 입니다.")
        String standardYear,

        @NotNull(message = "대분류는 필수 입니다.")
        String largeExerciseFacility,

        @NotNull(message = "중분류는 필수 입니다.")
        String middleExerciseFacility,

        @NotNull(message = "항목은 필수 입니다.")
        String itemName,

        @NotNull(message = "값은 필수 입니다.")
        String dataValue
) {
        public ExercisePostDto {
                if (standardYear == null) {
                    throw new IllegalArgumentException("must not be null");
                }
                if (largeExerciseFacility == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (middleExerciseFacility == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (itemName == null) {
                        throw new IllegalArgumentException("must not be null");
                }
                if (dataValue == null) {
                        throw new IllegalArgumentException("must not be null");
                }
        }
}
