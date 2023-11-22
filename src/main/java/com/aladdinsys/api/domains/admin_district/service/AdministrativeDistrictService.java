package com.aladdinsys.api.domains.admin_district.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPatchDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPostDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.entity.AdministrativeDistrict;
import com.aladdinsys.api.domains.admin_district.repository.AdministrativeDistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministrativeDistrictService {

    private final AdministrativeDistrictRepository repository;

    @Transactional(readOnly = true)
    public AdministrativeDistrictResponseDto findById(final Long id) {
        AdministrativeDistrict entity = repository.findAdministrativeDistrictById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return AdministrativeDistrictResponseDto.builder()
                .id(entity.getId())
                .standardYear(entity.getStandardYear())
                .cityCountyDistrictName(entity.getCityCountyDistrictName())
                .administrationName(entity.getAdministrationName())
                .itemName(entity.getItemName())
                .dataValue(entity.getDataValue())
                .build();

    }

    @Transactional(readOnly = true)
    public List<AdministrativeDistrictResponseDto> findAll() {
        List<AdministrativeDistrict> entites = repository.findAll();

        return entites.stream()
                .map(entity -> AdministrativeDistrictResponseDto.of(
                        entity.getId(),
                        entity.getStandardYear(),
                        entity.getCityCountyDistrictName(),
                        entity.getAdministrationName(),
                        entity.getItemName(),
                        entity.getDataValue()))
                .toList();
    }

    @Transactional
    public Long save(final AdministrativeDistrictPostDto dto) {
        AdministrativeDistrict entity = AdministrativeDistrict.builder()
                .standardYear(dto.standardYear())
                .cityCountyDistrictName(dto.cityCountyDistrictName())
                .administrationName(dto.administrationName())
                .itemName(dto.itemName())
                .dataValue(dto.dataValue())
                .build();
        AdministrativeDistrict saved = repository.save(entity);
        return saved.getId();
    }

    @Transactional
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void put(final Long id, final AdministrativeDistrictPostDto dto) {
        AdministrativeDistrict fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        fetch.update(dto.standardYear(), dto.cityCountyDistrictName(), dto.administrationName(), dto.itemName(), dto.dataValue());
    }

    @Transactional
    public void patch(final Long id, final AdministrativeDistrictPatchDto dto) {
        AdministrativeDistrict fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional.ofNullable(dto.standardYear()).ifPresent(fetch::patchStandardYear);
        Optional.ofNullable(dto.cityCountyDistrictName()).ifPresent(fetch::patchCityCountyDistrictNameNm);
        Optional.ofNullable(dto.administrationName()).ifPresent(fetch::patchAdministrationName);
        Optional.ofNullable(dto.itemName()).ifPresent(fetch::patchItemName);
        Optional.ofNullable(dto.dataValue()).ifPresent(fetch::patchDataValue);

    }

}
