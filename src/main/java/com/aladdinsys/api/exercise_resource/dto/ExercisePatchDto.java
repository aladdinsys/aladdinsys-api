package com.aladdinsys.api.exercise_resource.dto;

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
