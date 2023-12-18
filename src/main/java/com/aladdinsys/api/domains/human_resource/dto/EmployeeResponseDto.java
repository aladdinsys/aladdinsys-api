/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.dto;

import lombok.Builder;

@Builder
public record EmployeeResponseDto(Long id, String name, DepartmentResponseDto department) {
  public static EmployeeResponseDto of(Long id, String name, DepartmentResponseDto department) {
    Long departmentId = department.id();
    String departmentName = department.name();

    return new EmployeeResponseDto(
        id, name, DepartmentResponseDto.of(departmentId, departmentName));
  }
}
