package com.aladdinsys.api.domains.grid.dto;

import com.aladdinsys.api.domains.grid.entity.GridItem;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record GridItemResponseDto(
        Long id,
        Integer width,
        Integer height,
        Integer x,
        Integer y,
        Long contentId
) {
    public static Set<GridItemResponseDto> of(final Set<GridItem> items) {

        Set<GridItemResponseDto> result = new HashSet<>();

        items.forEach(item -> {
            result.add(GridItemResponseDto.builder()
                    .id(item.getId())
                    .width(item.getWidth())
                    .height(item.getHeight())
                    .x(item.getX())
                    .y(item.getY())
                    .contentId(item.getContentId())
                    .build());
        });

        return result;
    }
}
