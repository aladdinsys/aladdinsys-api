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
    @Column(name = "id", nullable = false)
    public Long Id;

    @Column(name = "std_yy", nullable = false, length = 4)
    public String stdYy;

    @Column(name = "phsrtn_fclty_lclas_nm", nullable = false, length = 20)
    public String largeExerciseFacility;

    @Column(name = "phsrtn_fclty_mclas_nm", nullable = false, length = 20)
    public String middleExerciseFacility;

    @Column(name = "iem_nm", nullable = false, length = 20)
    public String itemNm;

    @Column(name = "data_val", nullable = false, length = 20)
    public String dataValue;

    @Builder
    public ExerciseEntity(Long Id,
                          String stdYy,
                          String largeExerciseFacility,
                          String middleExerciseFacility,
                          String itemNm,
                          String dataValue
    ){
        this.Id = Id;
        this.stdYy = stdYy;
        this.largeExerciseFacility = largeExerciseFacility;
        this.middleExerciseFacility = middleExerciseFacility;
        this.itemNm = itemNm;
        this.dataValue = dataValue;
    }

    public void update(String stdYy,
                       String largeExerciseFacility,
                       String middleExerciseFacility,
                       String itemNm,
                       String dataValue
    ) {
        this.stdYy = stdYy;
        this.largeExerciseFacility = largeExerciseFacility;
        this.middleExerciseFacility = middleExerciseFacility;
        this.itemNm = itemNm;
        this.dataValue = dataValue;
    }

    public void patchStdYy(String stdYy) {this.stdYy = stdYy;}

    public void patchLargeExerciseFacility(String largeExerciseFacility) {this.largeExerciseFacility = largeExerciseFacility;}

    public void patchMiddleExerciseFacility(String middleExerciseFacility) {this.middleExerciseFacility = middleExerciseFacility;}

    public void patchItemNm(String itemNm) {this.itemNm = itemNm;}

    public void patchDataValue(String dataValue) {this.dataValue = dataValue;}


}
