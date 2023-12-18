/* (C) 2023 */
package com.aladdinsys.api.domains.exercise.dto;

import lombok.Builder;

@Builder
public record ExercisePatchDto(
    Long id,
    String standardYear,
    String largeExerciseFacility,
    String middleExerciseFacility,
    String itemName,
    String dataValue) {}
