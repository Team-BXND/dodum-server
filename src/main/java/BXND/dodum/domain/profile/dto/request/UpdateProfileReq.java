package BXND.dodum.domain.profile.dto.request;

import BXND.dodum.domain.auth.entity.Club;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileReq(
        int grade,
        int class_no,
        int student_no,
        @NotBlank(message = "휴대전화는 필수입력입니다.")
        String phone,
        @NotBlank(message = "이메일은 필수 입력입니다.")
        String email,
        Club club
){
}
