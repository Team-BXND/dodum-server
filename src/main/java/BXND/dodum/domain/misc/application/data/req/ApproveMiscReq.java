package BXND.dodum.domain.misc.application.data.req;

import jakarta.validation.constraints.NotNull;

public record ApproveMiscReq(
    @NotNull Long id
) {
}