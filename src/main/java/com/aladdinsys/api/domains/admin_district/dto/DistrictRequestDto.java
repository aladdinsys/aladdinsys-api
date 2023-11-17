package com.aladdinsys.api.domains.admin_district.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
public class DistrictRequestDto {

    @NotNull(message = "연도는 필수입니다.")
    private Integer stdYy;

    @NotNull(message = "시군구는 필수입니다.")
    @NotBlank(message = "시군구 공백이 될 수 없습니다.")
    private String signguNm;

    @NotNull(message = "행정동 이름은 필수입니다.")
    @NotBlank(message = "행정구은 공백이 될 수 없습니다.")
    private String adstrdNm;

    @NotNull(message = "항목 이름은 필수입니다.")
    @NotBlank(message = "항목 공백이 될 수 없습니다.")
    private String iemNm;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+|-$", message = "데이터 값은 숫자이거나 '-'이어야 합니다.")
    private String dataVal;
}
