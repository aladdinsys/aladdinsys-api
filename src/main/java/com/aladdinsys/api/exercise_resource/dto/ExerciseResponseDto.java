package com.aladdinsys.api.exercise_resource.dto;

import lombok.Builder;

@Builder
public record ExerciseResponseDto(
        Long dataId,
        String stdYy,
        String phsrtnFcltyLclasNm,
        String phsrtnFcltyMclasNm,
        String iemNm,
        String dataVal
) {
    public static ExerciseResponseDto of(Long dataId,
                                         String stdYy,
                                         String phsrtnFcltyLclasNm,
                                         String phsrtnFcltyMclasNm,
                                         String iemNm,
                                         String dataVal
    ) {
        return new ExerciseResponseDto(dataId, stdYy, phsrtnFcltyLclasNm, phsrtnFcltyMclasNm, iemNm, dataVal);
    }
}
