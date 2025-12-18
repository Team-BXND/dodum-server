package BXND.dodum.domain.contest.exception;

import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.StatusCode;

public class ContestException extends ApplicationException {
    public ContestException(StatusCode statusCode) {
            super(statusCode);
        }
        public ContestException(StatusCode statusCode, Throwable cause) {
            super(statusCode, cause);
        }
        public ContestException(StatusCode statusCode, String message) {
            super(statusCode, message);
        }
}
