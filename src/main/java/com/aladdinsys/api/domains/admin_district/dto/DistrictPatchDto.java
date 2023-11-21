package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

@Builder
public record DistrictPatchDto(
        Long id,
        String stdYy,
        String signguNm,
        String adstrdNm,
        String iemNm,
        String dataVal
) {}
