package com.aladdinsys.api.exercise.dto;

import lombok.Builder;

@Builder
public record ExercisePatchDto(

        Long id,
        String standardYear,
        String largeExerciseFacility,
        String middleExerciseFacility,
        String itemName,
        String dataValue
) {
}
