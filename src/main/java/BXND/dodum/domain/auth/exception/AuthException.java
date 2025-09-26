package com.GoWalk.domain.auth.exception;

import com.GoWalk.global.exception.exception.ApplicationException;
import com.GoWalk.global.exception.status_code.StatusCode;

public class AuthException extends ApplicationException {

  public AuthException(StatusCode statusCode) {
    super(statusCode);
  }

  public AuthException(StatusCode statusCode, Throwable cause) {
    super(statusCode, cause);
  }

  public AuthException(StatusCode statusCode, String message) {
    super(statusCode, message);
  }
}
