package com.aladdinsys.api.domains.grid.repository;

import com.aladdinsys.api.domains.grid.entity.GridPreset;

import java.util.List;

public interface CustomGridRepository {

    List<GridPreset> findAll();
}
