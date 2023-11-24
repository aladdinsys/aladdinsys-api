package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.AdministrativeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativeDistrictRepository extends JpaRepository<AdministrativeDistrict, Long>, CustomAdministrativeDistrictRepository {

}
