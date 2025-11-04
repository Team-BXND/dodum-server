package BXND.dodum.domain.archive.exception;

import org.springframework.http.HttpStatus;

public enum ArchiveErrorCode {
    // 공통
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ARCHIVE_INTERNAL_ERROR", "서버 오류가 발생했습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "ARCHIVE_VALIDATION_FAILED", "요청 검증에 실패했습니다."),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "ARCHIVE_UNAUTHENTICATED", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "ARCHIVE_FORBIDDEN", "접근 권한이 없습니다."),
    // 리소스
    NOT_FOUND(HttpStatus.NOT_FOUND, "ARCHIVE_NOT_FOUND", "게시글을 찾을 수 없습니다.");

    public final HttpStatus httpStatus;
    public final String code;
    public final String defaultMessage;

    ArchiveErrorCode(HttpStatus httpStatus, String code, String defaultMessage) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
