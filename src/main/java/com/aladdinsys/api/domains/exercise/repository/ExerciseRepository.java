/* (C) 2023 */
package com.aladdinsys.api.domains.exercise.repository;

import com.aladdinsys.api.domains.exercise.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {}
