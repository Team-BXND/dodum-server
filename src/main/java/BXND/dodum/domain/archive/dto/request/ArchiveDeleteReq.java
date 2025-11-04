package BXND.dodum.domain.archive.dto.request;

import jakarta.validation.constraints.NotNull;

public record ArchiveDeleteReq(
        @NotNull Long archiveId
) {}