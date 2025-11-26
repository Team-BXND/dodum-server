package BXND.dodum.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordReq(
        @Email @NotBlank String email,
        @NotBlank(message = "새 비밀번호 입력은 필수입니다.") String newPassword
) {
}
