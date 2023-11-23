package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record ExerciseRequestDto(
        @NotNull(message = "년도는 필수 입니다.")
        String standardYear,
        @NotNull(message = "체육시설 대분류는 필수 입니다.")
        String largeExerciseFacility,
        @NotNull(message = "체육시설 중분류는 필수 입니다.")
        String middleExerciseFacility,
        @NotNull(message = "항목은 필수 입니다.")
        String itemName,
        @NotNull(message = "값은 필수 입니다.")
        String dataValue
) {
    public ExerciseRequestDto {
        if (standardYear == null || largeExerciseFacility == null || middleExerciseFacility == null || itemName == null || dataValue == null) {
            throw new IllegalArgumentException("must not be null");
        }
    }
}
