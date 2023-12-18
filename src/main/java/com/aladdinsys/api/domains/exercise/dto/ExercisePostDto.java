/* (C) 2023 */
package com.aladdinsys.api.domains.exercise.dto;

import javax.validation.constraints.NotNull;

public record ExercisePostDto(
    @NotNull(message = "기준년도 는 필수 입니다.") String standardYear,
    @NotNull(message = "대 분류는 필수 입니다.") String largeExerciseFacility,
    @NotNull(message = "중 분류는 필수 입니다.") String middleExerciseFacility,
    @NotNull(message = "항목은 필수 입니다.") String itemName,
    @NotNull(message = "값은 필수 입니다.") String dataValue) {}
