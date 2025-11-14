package BXND.dodum.domain.contest.exception;

import BXND.dodum.global.exception.status_code.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContestStatusCode implements StatusCode {

    CONTEST_NOT_FOUND("CONTEST_NOT_FOUND", "존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("UNAUTHORIZED", "권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
