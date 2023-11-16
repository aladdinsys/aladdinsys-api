package com.aladdinsys.api.domains.human_resource.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import com.aladdinsys.api.domains.human_resource.entity.Department;
import com.aladdinsys.api.domains.human_resource.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    @Transactional
    public Long save(final DepartmentRequestDto dto) {

        Department entity = Department.builder()
                .name(dto.name())
                .build();

        Department saved = repository.save(entity);

        return saved.getId();
    }
    @Transactional(readOnly = true)
    public DepartmentResponseDto findById(final Long id) {
        Department entity = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return DepartmentResponseDto.of(entity.getId(), entity.getName());
    }
    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> findAll() {

        List<Department> entities = repository.findAll();

        return entities.stream()
                .map(entity -> DepartmentResponseDto.of(entity.getId(), entity.getName()))
                .toList();
    }

    @Transactional
    public void updateById(final Long id, final DepartmentRequestDto dto) {
        Department fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        fetch.update(dto.name());
    }

    @Transactional
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }


}
