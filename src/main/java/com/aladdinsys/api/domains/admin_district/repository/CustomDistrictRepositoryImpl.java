
package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.District;
import com.aladdinsys.api.domains.admin_district.entity.QDistrict;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomDistrictRepositoryImpl implements CustomDistrictRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<District> findDistrictById(Long id) {
        District district = queryFactory
                .select(QDistrict.district)
                .from(QDistrict.district)
                .where(QDistrict.district.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(district);
    }

}


