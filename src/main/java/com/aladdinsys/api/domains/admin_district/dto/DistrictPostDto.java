package com.aladdinsys.api.domains.admin_district.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record DistrictPostDto (

    @NotNull(message = "연도는 필수입니다.")
    Integer stdYy,

    @NotNull(message = "시군구는 필수입니다.")
    @NotBlank(message = "시군구는 공백이 될 수 없습니다.")
    String signguNm,

    @NotNull(message = "행정동 이름은 필수입니다.")
    @NotBlank(message = "행정동은 공백이 될 수 없습니다.")
    String adstrdNm,

    @NotNull(message = "항목 이름은 필수입니다.")
    @NotBlank(message = "항목 공백이 될 수 없습니다.")
    String iemNm,

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+|-$", message = "데이터 값은 숫자이거나 '-'이어야 합니다.")
    String dataVal
) {

    public DistrictPostDto {
        if ( stdYy == null ) {
            throw new IllegalArgumentException("stdYy must not be null");
        }
        if ( signguNm == null ) {
            throw new IllegalArgumentException("signguNm must not be null");
        }
        if ( adstrdNm == null ) {
            throw new IllegalArgumentException("adstrdNm must not be null");
        }
        if ( iemNm == null ) {
            throw new IllegalArgumentException("iemNm must not be null");
        }
        if ( dataVal == null ) {
            throw new IllegalArgumentException("dataVal must not be null");
        }

    }

}
