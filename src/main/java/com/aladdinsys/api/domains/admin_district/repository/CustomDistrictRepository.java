package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.District;

import java.util.Optional;

public interface CustomDistrictRepository {
    Optional<District> findDistrictById(Long id);
}
