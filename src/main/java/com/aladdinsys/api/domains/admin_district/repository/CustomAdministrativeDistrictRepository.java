package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.AdministrativeDistrict;

import java.util.Optional;

public interface CustomAdministrativeDistrictRepository {
    Optional<AdministrativeDistrict> findAdministrativeDistrictById(Long id);
}
