package BXND.dodum.domain.auth.exception;

import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.StatusCode;

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
