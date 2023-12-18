/* (C) 2023 */
package com.aladdinsys.api.domains.admin_district.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "tb_administrative_district",
    uniqueConstraints = {
      @UniqueConstraint(
          columnNames = {
            "standard_year",
            "city_county_district_name",
            "administration_name",
            "item_name",
            "data_value"
          })
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdministrativeDistrict extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "standard_year", nullable = false)
  private String standardYear;

  @Column(name = "city_county_district_name", nullable = false)
  private String cityCountyDistrictName;

  @Column(name = "administration_name", nullable = false)
  private String administrationName;

  @Column(name = "item_name", nullable = false)
  private String itemName;

  @Column(name = "data_value", nullable = true)
  private String dataValue;

  @Builder
  public AdministrativeDistrict(
      Long id,
      String standardYear,
      String cityCountyDistrictName,
      String administrationName,
      String itemName,
      String dataValue) {
    this.id = id;
    this.standardYear = standardYear;
    this.cityCountyDistrictName = cityCountyDistrictName;
    this.administrationName = administrationName;
    this.itemName = itemName;
    this.dataValue = dataValue;
  }

  public void update(
      String standardYear,
      String cityCountyDistrictName,
      String administrationName,
      String itemName,
      String dataValue) {
    this.standardYear = standardYear;
    this.cityCountyDistrictName = cityCountyDistrictName;
    this.administrationName = administrationName;
    this.itemName = itemName;
    this.dataValue = dataValue;
  }

  public void patchStandardYear(String standardYear) {
    this.standardYear = standardYear;
  }

  public void patchCityCountyDistrictNameNm(String cityCountyDistrictName) {
    this.cityCountyDistrictName = cityCountyDistrictName;
  }

  public void patchAdministrationName(String administrationName) {
    this.administrationName = administrationName;
  }

  public void patchItemName(String itemName) {
    this.itemName = itemName;
  }

  public void patchDataValue(String dataValue) {
    this.dataValue = dataValue;
  }
}
