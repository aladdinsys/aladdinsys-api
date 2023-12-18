/* (C) 2023 */
package com.aladdinsys.api.domains.grid.dto;

import java.util.Set;
import javax.validation.constraints.*;
import org.jetbrains.annotations.Nullable;

public record GridRequestDto(
    @NotNull(message = "이름은 필수 입니다.")
        @NotBlank(message = "이름은 공백이 될 수 없습니다.")
        @Size(max = 20, message = "이름은 20자 이하 여야 합니다.")
        String name,
    @Nullable
        @Min(value = 0, message = "Cols 는 0 보다 커야 한다.")
        @Max(value = 100, message = "Cols 는 100 보다 작아야 한다.")
        Integer cols,
    @Nullable
        @Min(value = 0, message = "Rows 는 0 보다 커야 한다.")
        @Max(value = 100, message = "Rows 는 100 보다 작아야 한다.")
        Integer rows,
    @Nullable Set<GridItemRequestDto> items) {}
