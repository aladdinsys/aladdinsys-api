package com.aladdinsys.api.domains.grid.dto;

import com.aladdinsys.api.domains.grid.entity.GridPreset;
import lombok.Builder;

import java.util.Set;

@Builder
public record GridResponseDto(
        Long id,
        String name,
        Integer rows,
        Integer cols,
        Set<GridItemResponseDto> items
)
{
    public static GridResponseDto of(final GridPreset preset) {
        return new GridResponseDto(preset.getId(), preset.getName(), preset.getRows(), preset.getCols(), GridItemResponseDto.of(preset.getItems()));
    }

}
