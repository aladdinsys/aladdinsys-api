package com.aladdinsys.api.domains.human_resource.dto;

import com.aladdinsys.api.domains.human_resource.entity.Department;

public record EmployeeResponseDto(
        Long id,
        String name,
        DepartmentResponseDto department
) {
    public static EmployeeResponseDto of(Long id, String name, Department department) {
        Long departmentId = department.getId();
        String departmentName = department.getName();

        return new EmployeeResponseDto(id, name, DepartmentResponseDto.of(departmentId, departmentName));
    }

}
