package com.aladdinsys.api.domains.human_resource.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record DepartmentRequestDto(
        @NotNull(message = "이름은 필수 입니다.")
        @NotBlank(message = "이름은 공백이 될 수 없습니다.")
        String name
) {
        public DepartmentRequestDto {
                if (name == null) {
                        throw new IllegalArgumentException("name must not be null");
                }
        }
}
