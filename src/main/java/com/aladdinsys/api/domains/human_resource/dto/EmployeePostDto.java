/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EmployeePostDto(
    @NotNull(message = "이름은 필수 입니다.") @NotBlank(message = "이름은 공백이 될 수 없습니다.") String name,
    @NotNull(message = "부서는 필수 입니다.") Long departmentId) {
  public EmployeePostDto {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null");
    }
    if (departmentId == null) {
      throw new IllegalArgumentException("departmentId must not be null");
    }
  }
}
