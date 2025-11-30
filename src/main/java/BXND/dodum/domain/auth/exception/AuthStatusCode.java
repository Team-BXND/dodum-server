package BXND.dodum.domain.auth.exception;

import BXND.dodum.global.exception.status_code.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthStatusCode implements StatusCode {
  INVALID_JWT("INVALID_JWT", "유효하지 않은 JWT입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_CREDENTIALS("INVALID_CREDENTIALS", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
  USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수 없습니다.",  HttpStatus.UNAUTHORIZED),
  ALREADY_EXIST_ACCOUNT("ALREADY_EXIST_ACCOUNT", "이미 존재하는 계정입니다.", HttpStatus.CONFLICT),
  ACCOUNT_LOCKED("ACCOUNT_LOCKED", "계정이 잠겨 있습니다.", HttpStatus.UNAUTHORIZED),
  ACCOUNT_DISABLED("ACCOUNT_DISABLED", "계정이 비활성화되었습니다.", HttpStatus.UNAUTHORIZED),
  EMAIL_VERIFICATION_FAILED("EMAIL_VERIFICATION_FAILED", "이메일 인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),
  ACCESS_DENIED("ACCESS_DENIED", "권한이 없습니다.", HttpStatus.FORBIDDEN),
  PASSWORD_MISMATCH("PASSWORD_MISMATCH", "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
