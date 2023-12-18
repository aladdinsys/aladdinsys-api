/* (C) 2023 */
package com.aladdinsys.api.common.exception;

import lombok.Builder;

@Builder
public record ValidationError(String fieldName, String errorMessage) {}
