package BXND.dodum.domain.misc.dto.req;

import BXND.dodum.domain.misc.dto.MiscCategoryE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMiscReq(
    @NotBlank String title,
    @NotBlank String content,
    @NotNull MiscCategoryE category
) {
}