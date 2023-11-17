package com.aladdinsys.api.domains.admin_district.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.admin_district.dto.DistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.entity.District;
import com.aladdinsys.api.domains.admin_district.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository repository;

    @Transactional(readOnly = true)
    public DistrictResponseDto findById(final Long id) {
        District entity = repository.findDistrictById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return DistrictResponseDto.builder()
                        .id(entity.getId())
                        .yearValue(entity.getYearValue())
                        .regionName(entity.getRegionName())
                        .districtName(entity.getDistrictName())
                        .itemName(entity.getItemName())
                        .dataValue(entity.getDataValue())
                        .build();

    }

}
