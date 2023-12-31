/* (C) 2023 */
package com.aladdinsys.api.domains.exercise.dto;

import lombok.Builder;

@Builder
public record ExerciseResponseDto(
    Long id,
    String standardYear,
    String largeExerciseFacility,
    String middleExerciseFacility,
    String itemName,
    String dataValue) {
  public static ExerciseResponseDto of(
      Long id,
      String standardYear,
      String largeExerciseFacility,
      String middleExerciseFacility,
      String itemName,
      String dataValue) {
    return new ExerciseResponseDto(
        id, standardYear, largeExerciseFacility, middleExerciseFacility, itemName, dataValue);
  }
}
