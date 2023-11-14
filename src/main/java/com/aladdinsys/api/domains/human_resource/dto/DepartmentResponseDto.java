package com.aladdinsys.api.domains.human_resource.dto;

import lombok.Builder;

@Builder
public record DepartmentResponseDto(
        Long id,
        String name
) {
    public static DepartmentResponseDto of(Long id, String name) {
        return new DepartmentResponseDto(id, name);
    }
}
