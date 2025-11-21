package BXND.dodum.domain.archive.exception;

import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.StatusCode;

public class ArchiveException extends ApplicationException {
    public ArchiveException(StatusCode statusCode) {
        super(statusCode);
    }
    public ArchiveException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }
}
