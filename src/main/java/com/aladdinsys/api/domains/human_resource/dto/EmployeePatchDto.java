/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.dto;

import lombok.Builder;

@Builder
public record EmployeePatchDto(String name, Long departmentId) {}
