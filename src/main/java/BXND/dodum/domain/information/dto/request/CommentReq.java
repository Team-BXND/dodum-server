package BXND.dodum.domain.information.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentReq(
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String comment
) {
}
