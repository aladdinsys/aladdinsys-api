package com.aladdinsys.api.domains.admin_district.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "tods0277")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class District extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year_value", nullable = false)
    private Integer yearValue;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "data_value", nullable = true)
    private String dataValue;

    @Builder
    public District(Long id, Integer yearValue, String regionName, String districtName, String itemName, String dataValue) {
        this.id = id;
        this.yearValue = yearValue;
        this.regionName = regionName;
        this.districtName = districtName;
        this.itemName = itemName;
        this.dataValue = dataValue;
    }

    public void update(Integer yearValue, String regionName, String districtName, String itemName, String dataValue) {
        this.yearValue = yearValue;
        this.regionName = regionName;
        this.districtName = districtName;
        this.itemName = itemName;
        this.dataValue = dataValue;
    }

}
