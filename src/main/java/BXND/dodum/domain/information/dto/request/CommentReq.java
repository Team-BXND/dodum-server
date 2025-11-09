package BXND.dodum.domain.information.dto.request;

import jakarta.validation.constraints.NotNull;

public record CommentReq(
        @NotNull
        String comment
) {
}
