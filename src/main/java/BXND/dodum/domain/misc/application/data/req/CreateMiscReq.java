package BXND.dodum.domain.misc.application.data.req;

import jakarta.validation.constraints.NotBlank;

public record CreateMiscReq(
    @NotBlank String title,
    @NotBlank String content
) {
}