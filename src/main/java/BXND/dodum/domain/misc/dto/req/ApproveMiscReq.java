package BXND.dodum.domain.misc.dto.req;

import jakarta.validation.constraints.NotNull;

public record ApproveMiscReq(
    @NotNull Long id
) {
}