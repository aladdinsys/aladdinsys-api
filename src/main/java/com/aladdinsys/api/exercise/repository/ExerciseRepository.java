package com.aladdinsys.api.exercise.repository;

import com.aladdinsys.api.exercise.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

}
