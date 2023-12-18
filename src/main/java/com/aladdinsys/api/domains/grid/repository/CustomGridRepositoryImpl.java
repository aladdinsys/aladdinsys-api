/* (C) 2023 */
package com.aladdinsys.api.domains.grid.repository;

import static com.aladdinsys.api.domains.grid.entity.QGridItem.gridItem;
import static com.aladdinsys.api.domains.grid.entity.QGridPreset.gridPreset;

import com.aladdinsys.api.domains.grid.entity.GridPreset;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomGridRepositoryImpl implements CustomGridRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<GridPreset> findAll() {
    return queryFactory
        .selectFrom(gridPreset)
        .leftJoin(gridPreset.items, gridItem)
        .fetchJoin()
        .fetch();
  }
}
