package BXND.dodum.domain.misc.dto.req;

import BXND.dodum.domain.misc.dto.MiscCategoryE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMiscReq(
    @NotBlank String title,
    @NotBlank String content,
    @NotNull MiscCategoryE category
    ) {
}