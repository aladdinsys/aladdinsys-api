package com.aladdinsys.api.domains.grid.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.grid.dto.GridItemRequestDto;
import com.aladdinsys.api.domains.grid.dto.GridRequestDto;
import com.aladdinsys.api.domains.grid.dto.GridResponseDto;
import com.aladdinsys.api.domains.grid.entity.GridItem;
import com.aladdinsys.api.domains.grid.entity.GridPreset;
import com.aladdinsys.api.domains.grid.repository.GridItemRepository;
import com.aladdinsys.api.domains.grid.repository.GridPresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GridService {

    private final GridPresetRepository presetRepository;

    private final GridItemRepository itemRepository;

    @Transactional(readOnly = true)
    public GridResponseDto findById(final Long id) {
        return GridResponseDto.of(presetRepository
                                    .findById(id)
                                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public List<GridResponseDto> findAll() {
        return presetRepository.findAll()
                .stream()
                .map(GridResponseDto::of)
                .toList();
    }


    @Transactional
    public Long save(GridRequestDto dto) {

        GridPreset preset = GridPreset.builder()
                .name(dto.name())
                .cols(dto.cols())
                .rows(dto.rows())
                .build();

        final GridPreset saved = presetRepository.save(preset);

        saveGridItems(dto, saved);

        return saved.getId();
    }

    @Transactional
    public void update(Long id, GridRequestDto dto) {

        GridPreset fetched = presetRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        fetched.update(dto.name(), dto.cols(), dto.rows());
        fetched.clearItems();

        saveGridItems(dto, fetched);
    }

    private void saveGridItems(GridRequestDto dto, GridPreset fetched) {
        Set<GridItemRequestDto> items = dto.items();

        if(items != null) {
            for(GridItemRequestDto item : items) {
                GridItem savedItem = itemRepository.save(GridItem.builder()
                        .preset(fetched)
                        .x(item.x())
                        .y(item.y())
                        .width(item.width())
                        .height(item.height())
                        .contentId(item.contentId())
                        .build());

                fetched.addItem(savedItem);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        GridPreset fetched = presetRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        presetRepository.delete(fetched);
    }
}
