/* (C) 2023 */
package com.aladdinsys.api.common.constant;

import org.springframework.http.HttpStatus;

public interface Code {

  HttpStatus getHttpStatus();

  String getMessage();

  String getStatusName();

  Integer getStatusValue();
}
