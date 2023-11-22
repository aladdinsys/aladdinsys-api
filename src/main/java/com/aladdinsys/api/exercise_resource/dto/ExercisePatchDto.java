package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

@Builder
public record ExercisePatchDto(

        Long dataId,
        String stdYy,
        String phsrtnFcltyLclasNm,
        String phsrtnFcltyMclasNm,
        String iemNm,
        String dataVal
) {
}
