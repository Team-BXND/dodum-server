package BXND.dodum.domain.misc.application.data.req;

import jakarta.validation.constraints.NotNull;

public record GetMiscReq(
    @NotNull Long id
) {
}