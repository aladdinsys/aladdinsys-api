package com.aladdinsys.api.domains.admin_district.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.admin_district.dto.DistrictPostDto;
import com.aladdinsys.api.domains.admin_district.dto.DistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.entity.District;
import com.aladdinsys.api.domains.admin_district.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository repository;

    @Transactional(readOnly = true)
    public DistrictResponseDto findById(final Long id) {
        District entity = repository.findDistrictById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return DistrictResponseDto.builder()
                .id(entity.getId())
                .stdYy(entity.getStdYy())
                .signguNm(entity.getSignguNm())
                .adstrdNm(entity.getAdstrdNm())
                .iemNm(entity.getAdstrdNm())
                .dataVal(entity.getIemNm())
                .build();

    }

    @Transactional
    public List<DistrictResponseDto> findAll() {
        List<District> entites = repository.findAll();

        return entites.stream()
                .map(entity -> DistrictResponseDto.of(
                        entity.getId(),
                        entity.getStdYy(),
                        entity.getSignguNm(),
                        entity.getAdstrdNm(),
                        entity.getAdstrdNm(),
                        entity.getIemNm()))
                .toList();
    }

    @Transactional
    public Long save(final DistrictPostDto dto) {
        District entity = District.builder()
                .stdYy(dto.stdYy())
                .signguNm(dto.signguNm())
                .adstrdNm(dto.adstrdNm())
                .iemNm(dto.iemNm())
                .dataVal(dto.dataVal())
                .build();
        District saved = repository.save(entity);
        return saved.getId();
    }

    @Transactional
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void put(final Long id, final DistrictPostDto dto) {
        District fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        fetch.update(dto.stdYy(), dto.signguNm(), dto.iemNm(), dto.adstrdNm(), dto.dataVal());
    }

}
