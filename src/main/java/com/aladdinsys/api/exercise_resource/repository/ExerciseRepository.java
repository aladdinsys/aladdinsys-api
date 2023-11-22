package com.aladdinsys.api.exercise_resource.repository;

import com.aladdinsys.api.exercise_resource.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

}
