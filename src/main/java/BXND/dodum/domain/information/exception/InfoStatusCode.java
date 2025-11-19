package BXND.dodum.domain.information.exception;

import BXND.dodum.global.exception.status_code.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InfoStatusCode implements StatusCode {
        INFO_NOT_FOUND("INFO_NOT_FOUND", "존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND),
        UNAUTHORIZED("UNAUTHORIZED", "권한이 없습니다.", HttpStatus.FORBIDDEN);

        private final String code;
        private final String message;
        private final HttpStatus httpStatus;
}
