package com.aladdinsys.api.domains.grid.repository;

import com.aladdinsys.api.domains.grid.entity.GridItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridItemRepository extends JpaRepository<GridItem, Long> {
}
