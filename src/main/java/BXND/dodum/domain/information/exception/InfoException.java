package BXND.dodum.domain.information.exception;

import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.StatusCode;

public class InfoException extends ApplicationException {

  public InfoException(StatusCode statusCode) {
    super(statusCode);
  }

  public InfoException(StatusCode statusCode, Throwable cause) {
    super(statusCode, cause);
  }

  public InfoException(StatusCode statusCode, String message) {
    super(statusCode, message);
  }
}
