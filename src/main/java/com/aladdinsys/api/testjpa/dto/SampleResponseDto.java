package com.aladdinsys.api.testjpa.dto;

import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import lombok.Builder;

@Builder
public record SampleResponseDto(
        Long data_id,
        String std_yy,
        String phsrtn_fclty_lclas_nm,
        String phsrtn_fclty_mclas_nm,
        String iem_nm,
        String data_val
) {
    public static SampleResponseDto of(Long data_id, String std_yy, String phsrtn_fclty_lclas_nm,
                                           String phsrtn_fclty_mclas_nm, String iem_nm, String data_val) {
        return new SampleResponseDto(data_id, std_yy, phsrtn_fclty_lclas_nm, phsrtn_fclty_mclas_nm, iem_nm, data_val);
    }
}
