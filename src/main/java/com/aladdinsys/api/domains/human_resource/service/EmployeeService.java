package com.aladdinsys.api.domains.human_resource.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeResponseDto;
import com.aladdinsys.api.domains.human_resource.entity.Department;
import com.aladdinsys.api.domains.human_resource.entity.Employee;
import com.aladdinsys.api.domains.human_resource.repository.DepartmentRepository;
import com.aladdinsys.api.domains.human_resource.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository repository;

    @Transactional
    public Long save(EmployeeRequestDto dto) {

        Employee entity = Employee.builder()
                .name(dto.name())
                .build();

        Employee saved = repository.save(entity);

        return saved.getId();
    }
    @Transactional(readOnly = true)
    public EmployeeResponseDto findById(Long id) {
        Employee entity = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return EmployeeResponseDto.of(entity.getId(), entity.getName(), entity.getDepartment());
    }
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findAll() {

        List<Employee> entities = repository.findAll();

        return entities.stream()
                .map(entity -> EmployeeResponseDto.of(entity.getId(), entity.getName(), entity.getDepartment()))
                .toList();
    }

    @Transactional
    public void updateById(Long id, EmployeeRequestDto dto) {
        Employee fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Department department = departmentRepository
                                    .findById(dto.departmentId())
                                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        fetch.update(dto.name(), department);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
