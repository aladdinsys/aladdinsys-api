package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

@Builder
public record DistrictResponseDto(
        Long id,
        String stdYy,
        String signguNm,
        String adstrdNm,
        String iemNm,
        String dataVal
) {
    public static DistrictResponseDto of(Long id, String stdYy, String signguNm, String adstrdNm, String iemNm, String dataVal) {
        return new DistrictResponseDto(id, stdYy, signguNm, adstrdNm, iemNm, dataVal);
    }
}
