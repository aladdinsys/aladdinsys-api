/* (C) 2023 */
package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

@Builder
public record AdministrativeDistrictPatchDto(
    Long id,
    String standardYear,
    String cityCountyDistrictName,
    String administrationName,
    String itemName,
    String dataValue) {}
