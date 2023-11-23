package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

@Builder
public record ExerciseResponseDto(
        Long Id,
        String stdYy,
        String largeExerciseFacility,
        String middleExerciseFacility,
        String itemNm,
        String dataValue
) {
    public static ExerciseResponseDto of(Long Id,
                                         String stdYy,
                                         String largeExerciseFacility,
                                         String middleExerciseFacility,
                                         String itemNm,
                                         String dataValue
    ) {
        return new ExerciseResponseDto(Id, stdYy, largeExerciseFacility, middleExerciseFacility, itemNm, dataValue);
    }
}
