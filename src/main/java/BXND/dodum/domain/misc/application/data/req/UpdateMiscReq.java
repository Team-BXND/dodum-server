package BXND.dodum.domain.misc.application.data.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMiscReq(
    @NotNull Long id,
    @NotBlank String title,
    @NotBlank String content
) {
}