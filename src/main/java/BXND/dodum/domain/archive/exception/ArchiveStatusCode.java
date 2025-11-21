package BXND.dodum.domain.archive.exception;

import BXND.dodum.global.exception.status_code.StatusCode;
import org.springframework.http.HttpStatus;

public enum ArchiveStatusCode implements StatusCode {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ARCHIVE_INTERNAL_ERROR", "서버 오류가 발생했습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "ARCHIVE_VALIDATION_FAILED", "요청 검증에 실패했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ARCHIVE_NOT_FOUND", "게시글을 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "ARCHIVE_FORBIDDEN", "접근 권한이 없습니다."),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "ARCHIVE_UNAUTHENTICATED", "인증이 필요합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ArchiveStatusCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override public HttpStatus getHttpStatus() { return httpStatus; }
    @Override public String getCode() { return code; }
    @Override public String getMessage() { return message; }
}
