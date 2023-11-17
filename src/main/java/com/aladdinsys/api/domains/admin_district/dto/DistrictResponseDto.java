package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

@Builder
public record DistrictResponseDto(
        Long id,
        Integer yearValue,
        String regionName,
        String districtName,
        String itemName,
        String dataValue
) {
    public static DistrictResponseDto of(Long id, Integer yearValue, String regionName, String districtName, String itemName, String dataValue) {
        return new DistrictResponseDto(id, yearValue, regionName, districtName, itemName, dataValue);
    }
}
