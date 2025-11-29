package BXND.dodum.domain.misc.dto.req;

import jakarta.validation.constraints.NotNull;

public record GetMiscReq(
    @NotNull Long id
) {
}