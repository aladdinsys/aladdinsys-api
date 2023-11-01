package com.aladdinsys.api.domains.duty.service;

import com.aladdinsys.api.domains.duty.dto.IncomeRequestDto;
import com.aladdinsys.api.domains.duty.dto.IncomeResponseDto;
import com.aladdinsys.api.domains.duty.entity.Income;
import com.aladdinsys.api.domains.duty.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository repository;

    @Transactional
    public Long save(IncomeRequestDto dto) {

        Income entity = Income.builder()
            .name(dto.name())
            .build();

        Income saved = repository.save(entity);

        return saved.getId();
    }
    @Transactional(readOnly = true)
    public IncomeResponseDto findById(Long id) {
        Income entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        return IncomeResponseDto.of(entity.getId(), entity.getName());
    }
    @Transactional(readOnly = true)
    public List<IncomeResponseDto> findAll() {

        List<Income> entities = repository.findAll();

        return entities.stream()
            .map(entity -> IncomeResponseDto.of(entity.getId(), entity.getName()))
            .toList();
    }

    @Transactional
    public void updateById(Long id, IncomeRequestDto dto) {
        Income fetch = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));

        fetch.update(dto.name());


    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
