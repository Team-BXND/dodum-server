package BXND.dodum.domain.contest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateContestReq(
        @NotBlank(message = "제목을 필수입력입니다.")
        String title,
        String subTitle,
        @NotBlank(message = "장소는 필수입력입니다.")
        String place,
        @NotBlank(message = "전화번호는 필수입력입니다.")
        String phone,
        @NotBlank(message = "이메일은 필수입력입니다.")
        @Email
        String email,
        String time,
        @NotBlank(message = "내용은 필수 입력입니다.")
        String content
) {
}
