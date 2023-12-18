/* (C) 2023 */
package com.aladdinsys.api.domains.grid.dto;

import javax.validation.constraints.NotNull;

public record GridItemRequestDto(
    @NotNull(message = "Width 는 필수 입니다.") Integer width,
    @NotNull(message = "Height 는 필수 입니다.") Integer height,
    @NotNull(message = "X 값은 필수 입니다.") Integer x,
    @NotNull(message = "Y 값은 필수 입니다.") Integer y,
    @NotNull(message = "ContentID 는 필수 입니다.") Long contentId) {
  public static GridItemRequestDto of(int width, int height, int x, int y, long contentId) {
    return new GridItemRequestDto(width, height, x, y, contentId);
  }
}
