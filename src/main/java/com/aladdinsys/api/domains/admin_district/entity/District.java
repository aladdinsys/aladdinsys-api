package com.aladdinsys.api.domains.admin_district.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "tods0277", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"std_yy", "signgu_nm", "adstrd_nm", "iem_nm", "data_val"})
})
//@Table(name = "tods0277")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class District extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "std_yy", nullable = false)
    private String stdYy;

    @Column(name = "signgu_nm", nullable = false)
    private String signguNm;

    @Column(name = "adstrd_nm", nullable = false)
    private String adstrdNm;

    @Column(name = "iem_nm", nullable = false)
    private String iemNm;

    @Column(name = "data_val", nullable = true)
    private String dataVal;

    @Builder
    public District(Long id, String stdYy, String signguNm, String adstrdNm, String iemNm, String dataVal) {
        this.id = id;
        this.stdYy = stdYy;
        this.signguNm = signguNm;
        this.adstrdNm = adstrdNm;
        this.iemNm = iemNm;
        this.dataVal = dataVal;
    }

    public void update(String stdYy, String signguNm, String adstrdNm, String iemNm, String dataVal) {
        this.stdYy = stdYy;
        this.signguNm = signguNm;
        this.adstrdNm = adstrdNm;
        this.iemNm = iemNm;
        this.dataVal = dataVal;
    }

    public void patchStdYy(String stdYy) {
        this.stdYy = stdYy;
    }

    public void patchSignguNm(String signguNm) {
        this.signguNm = signguNm;
    }

    public void patchAdstrdNm(String adstrdNm) {
        this.adstrdNm = adstrdNm;
    }

    public void patchIemNm(String iemNm) {
        this.iemNm = iemNm;
    }

    public void patchDataVal(String dataVal) {
        this.dataVal = dataVal;
    }

}
