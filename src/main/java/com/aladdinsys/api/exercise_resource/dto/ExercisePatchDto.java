package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

@Builder
public record ExercisePatchDto(

        Long Id,
        String stdYy,
        String largeExerciseFacility,
        String middleExerciseFacility,
        String itemNm,
        String dataValue
) {
}
