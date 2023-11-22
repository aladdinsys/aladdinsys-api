
package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.AdministrativeDistrict;
import com.aladdinsys.api.domains.admin_district.entity.QAdministrativeDistrict;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomAdministrativeDistrictRepositoryImpl implements CustomAdministrativeDistrictRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AdministrativeDistrict> findAdministrativeDistrictById(Long id) {
        AdministrativeDistrict district = queryFactory
                .select(QAdministrativeDistrict.administrativeDistrict)
                .from(QAdministrativeDistrict.administrativeDistrict)
                .where(QAdministrativeDistrict.administrativeDistrict.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(district);
    }

}


