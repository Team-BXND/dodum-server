package BXND.dodum.domain.auth.dto.request;

import BXND.dodum.domain.auth.entity.Club;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "id는 필수입니다.")
        String username,
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,
        @NotBlank(message = "이메일은 필수입니다.")
        @Email
        String email,
        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,
        String major,
        int grade,
        int class_no,
        int student_no,
        Club club,
        String history
) {
}
