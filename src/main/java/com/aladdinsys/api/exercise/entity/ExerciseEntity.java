package com.aladdinsys.api.exercise.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_exercise_facility",  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"standard_year", "large_exercise_facility", "middle_exercise_facility", "item_name"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "standard_year", nullable = false)
    public String standardYear;

    @Column(name = "large_exercise_facility", nullable = false)
    public String largeExerciseFacility;

    @Column(name = "middle_exercise_facility", nullable = false)
    public String middleExerciseFacility;

    @Column(name = "item_name", nullable = false)
    public String itemName;

    @Column(name = "data_value", nullable = false)
    public String dataValue;

    @Builder
    public ExerciseEntity(Long id,
                          String standardYear,
                          String largeExerciseFacility,
                          String middleExerciseFacility,
                          String itemName,
                          String dataValue
    ){
        this.id = id;
        this.standardYear = standardYear;
        this.largeExerciseFacility = largeExerciseFacility;
        this.middleExerciseFacility = middleExerciseFacility;
        this.itemName = itemName;
        this.dataValue = dataValue;
    }

    public void update(String standardYear,
                       String largeExerciseFacility,
                       String middleExerciseFacility,
                       String itemName,
                       String dataValue
    ) {
        this.standardYear = standardYear;
        this.largeExerciseFacility = largeExerciseFacility;
        this.middleExerciseFacility = middleExerciseFacility;
        this.itemName = itemName;
        this.dataValue = dataValue;
    }

    public void patchStdYy(String standardYear) {this.standardYear = standardYear;}

    public void patchLargeExerciseFacility(String largeExerciseFacility) {this.largeExerciseFacility = largeExerciseFacility;}

    public void patchMiddleExerciseFacility(String middleExerciseFacility) {this.middleExerciseFacility = middleExerciseFacility;}

    public void patchItemNm(String itemName) {this.itemName = itemName;}

    public void patchDataValue(String dataValue) {this.dataValue = dataValue;}


}
