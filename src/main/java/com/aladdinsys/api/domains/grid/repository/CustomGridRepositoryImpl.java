package com.aladdinsys.api.domains.grid.repository;

import com.aladdinsys.api.domains.grid.entity.GridPreset;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.aladdinsys.api.domains.grid.entity.QGridPreset.gridPreset;
import static com.aladdinsys.api.domains.grid.entity.QGridItem.gridItem;


@RequiredArgsConstructor
public class CustomGridRepositoryImpl implements CustomGridRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GridPreset> findAll() {
        return queryFactory
                .selectFrom(gridPreset)
                .leftJoin(gridPreset.items, gridItem).fetchJoin()
                .fetch();
    }
}
