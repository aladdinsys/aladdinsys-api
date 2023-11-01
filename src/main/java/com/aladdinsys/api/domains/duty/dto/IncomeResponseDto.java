package com.aladdinsys.api.domains.duty.dto;

public record IncomeResponseDto(
    Long id,
    String name

) {
    public IncomeResponseDto {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
    }

    public static IncomeResponseDto of(Long id, String name) {
        return new IncomeResponseDto(id, name);
    }


}
