package com.aladdinsys.api.domains.admin_district.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record AdministrativeDistrictPostDto(

    @NotNull(message = "연도는 필수입니다.")
    String standardYear,

    @NotNull(message = "시군구는 필수입니다.")
    @NotBlank(message = "시군구는 공백이 될 수 없습니다.")
    String cityCountyDistrictName,

    @NotNull(message = "행정동 이름은 필수입니다.")
    @NotBlank(message = "행정동은 공백이 될 수 없습니다.")
    String administrationName,

    @NotNull(message = "항목 이름은 필수입니다.")
    @NotBlank(message = "항목 공백이 될 수 없습니다.")
    String itemName,

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+|-$", message = "데이터 값은 숫자이거나 '-'이어야 합니다.")
    String dataValue
) {

    public AdministrativeDistrictPostDto {
        if ( standardYear == null ) {
            throw new IllegalArgumentException("standardYear must not be null");
        }
        if ( cityCountyDistrictName == null ) {
            throw new IllegalArgumentException("cityCountyDistrictName must not be null");
        }
        if ( administrationName == null ) {
            throw new IllegalArgumentException("administrationName must not be null");
        }
        if ( itemName == null ) {
            throw new IllegalArgumentException("itemName must not be null");
        }
        if ( dataValue == null ) {
            throw new IllegalArgumentException("dataValue must not be null");
        }

    }

}
