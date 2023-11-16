package com.aladdinsys.api.domains.human_resource.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeePatchDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeePostDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeResponseDto;
import com.aladdinsys.api.domains.human_resource.entity.Department;
import com.aladdinsys.api.domains.human_resource.entity.Employee;
import com.aladdinsys.api.domains.human_resource.repository.DepartmentRepository;
import com.aladdinsys.api.domains.human_resource.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository repository;

    @Transactional
    public Long save(final EmployeePostDto dto) {

        Department department = departmentRepository.findById(dto.departmentId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Employee entity = Employee.builder()
                .name(dto.name())
                .department(department)
                .build();

        Employee saved = repository.save(entity);

        return saved.getId();
    }
    @Transactional(readOnly = true)
    public EmployeeResponseDto findById(final Long id) {
        Employee entity = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return EmployeeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .department(DepartmentResponseDto.builder()
                        .id(entity.getDepartment().getId())
                        .name(entity.getDepartment().getName())
                        .build())
                .build();
    }
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findAll() {

        List<Employee> entities = repository.findAll();

        return entities.stream()
                .map(entity -> EmployeeResponseDto.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .department(DepartmentResponseDto.builder()
                                        .id(entity.getDepartment().getId())
                                        .name(entity.getDepartment().getName())
                                        .build())
                                .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> findByDepartment(final Long departmentId) {

        List<Employee> entities = repository.findEmployeeByDepartment(departmentId);

        return entities.stream()
                .map(entity -> EmployeeResponseDto.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .department(DepartmentResponseDto.builder()
                                            .id(entity.getDepartment().getId())
                                            .name(entity.getDepartment().getName())
                                            .build())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void put(final Long id, final EmployeePostDto dto) {
        Employee fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Department department = departmentRepository
                                    .findById(dto.departmentId())
                                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        fetch.update(dto.name(), department);
    }

    @Transactional
    public void patch(final Long id, final EmployeePatchDto dto) {

        Employee fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional.ofNullable(dto.name()).ifPresent(fetch::patchName);
        Optional.ofNullable(dto.departmentId()).ifPresent(departmentId -> {
            Department department = departmentRepository
                    .findById(departmentId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
            fetch.patchDepartment(department);
        });
    }

    @Transactional
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
