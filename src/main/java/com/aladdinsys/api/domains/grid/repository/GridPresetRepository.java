package com.aladdinsys.api.domains.grid.repository;

import com.aladdinsys.api.domains.grid.entity.GridPreset;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GridPresetRepository extends JpaRepository<GridPreset, Long>, CustomGridRepository {

    @Override
    @NotNull
    @Query("""
            SELECT p FROM GridPreset p
            JOIN FETCH p.items
            WHERE p.id = :id
            """)
    Optional<GridPreset> findById(@Param(value = "id") @NotNull Long id);

    Optional<Object> findByName(@Param(value = "name") @NotNull String name);
}
