package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

@Builder
public record AdministrativeDistrictResponseDto(
        Long id,
        String standardYear,
        String cityCountyDistrictName,
        String administrationName,
        String itemName,
        String dataValue
) {
    public static AdministrativeDistrictResponseDto of(Long id, String standardYear, String cityCountyDistrictName, String administrationName, String itemName, String dataValue) {
        return new AdministrativeDistrictResponseDto(id, standardYear, cityCountyDistrictName, administrationName, itemName, dataValue);
    }
}
