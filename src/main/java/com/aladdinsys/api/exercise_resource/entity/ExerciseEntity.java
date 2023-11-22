package com.aladdinsys.api.exercise_resource.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_exercise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    public Long dataId;

    @Column(name = "std_yy", nullable = false, length = 4)
    public String stdYy;

    @Column(name = "phsrtn_fclty_lclas_nm", nullable = false, length = 20)
    public String phsrtnFcltyLclasNm;

    @Column(name = "phsrtn_fclty_mclas_nm", nullable = false, length = 20)
    public String phsrtnFcltyMclasNm;

    @Column(name = "iem_nm", nullable = false, length = 20)
    public String iemNm;

    @Column(name = "data_val", nullable = false, length = 20)
    public String dataVal;

    @Builder
    public ExerciseEntity(Long dataId,
                          String stdYy,
                          String phsrtnFcltyLclasNm,
                          String phsrtnFcltyMclasNm,
                          String iemNm,
                          String dataVal
    ){
        this.dataId = dataId;
        this.stdYy = stdYy;
        this.phsrtnFcltyLclasNm = phsrtnFcltyLclasNm;
        this.phsrtnFcltyMclasNm = phsrtnFcltyMclasNm;
        this.iemNm = iemNm;
        this.dataVal = dataVal;
    }

    public void update(String stdYy,
                       String phsrtnFcltyLclasNm,
                       String phsrtnFcltyMclasNm,
                       String iemNm,
                       String dataVal
    ) {
        this.stdYy = stdYy;
        this.phsrtnFcltyLclasNm = phsrtnFcltyLclasNm;
        this.phsrtnFcltyMclasNm = phsrtnFcltyMclasNm;
        this.iemNm = iemNm;
        this.dataVal = dataVal;
    }

    public void patchStdYy(String stdYy) {this.stdYy = stdYy;}

    public void patchPhsrtnFcltyLclasNm(String phsrtnFcltyLclasNm) {this.phsrtnFcltyLclasNm = phsrtnFcltyLclasNm;}

    public void patchPhsrtnFcltyMclasNm(String phsrtnFcltyMclasNm) {this.phsrtnFcltyMclasNm = phsrtnFcltyMclasNm;}

    public void patchIemNm(String iemNm) {this.iemNm = iemNm;}

    public void patchDataVal(String dataVal) {this.dataVal = dataVal;}


}
