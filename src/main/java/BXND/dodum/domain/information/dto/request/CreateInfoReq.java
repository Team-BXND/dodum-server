package BXND.dodum.domain.information.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateInfoReq(
        @NotBlank(message = "제목은 필수 입력입니다.")
        String title,
        @NotBlank(message = "글의 내용은 필수 입력입니다.")
        String content,
        List<String> imageUrls
) {
}
