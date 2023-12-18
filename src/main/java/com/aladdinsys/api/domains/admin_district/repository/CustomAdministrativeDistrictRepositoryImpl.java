/* (C) 2023 */
package com.aladdinsys.api.domains.admin_district.repository;

import com.aladdinsys.api.domains.admin_district.entity.AdministrativeDistrict;
import com.aladdinsys.api.domains.admin_district.entity.QAdministrativeDistrict;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAdministrativeDistrictRepositoryImpl
    implements CustomAdministrativeDistrictRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<AdministrativeDistrict> findAdministrativeDistrictById(Long id) {
    AdministrativeDistrict district =
        queryFactory
            .select(QAdministrativeDistrict.administrativeDistrict)
            .from(QAdministrativeDistrict.administrativeDistrict)
            .where(QAdministrativeDistrict.administrativeDistrict.id.eq(id))
            .fetchOne();

    return Optional.ofNullable(district);
  }

  @Override
  public Optional<AdministrativeDistrict> findAdministrativeDistrictByColumns(
      String standardYear,
      String cityCountyDistrictName,
      String administrationName,
      String itemName,
      String dataValue) {

    AdministrativeDistrict district =
        queryFactory
            .select(QAdministrativeDistrict.administrativeDistrict)
            .from(QAdministrativeDistrict.administrativeDistrict)
            .where(
                QAdministrativeDistrict.administrativeDistrict
                    .standardYear
                    .eq(standardYear)
                    .and(
                        QAdministrativeDistrict.administrativeDistrict.cityCountyDistrictName.eq(
                            cityCountyDistrictName))
                    .and(
                        QAdministrativeDistrict.administrativeDistrict.administrationName.eq(
                            administrationName))
                    .and(QAdministrativeDistrict.administrativeDistrict.itemName.eq(itemName))
                    .and(QAdministrativeDistrict.administrativeDistrict.dataValue.eq(dataValue)))
            .fetchOne();

    return Optional.ofNullable(district);
  }
}
