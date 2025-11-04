package BXND.dodum.domain.archive.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArchiveUpdateReq(
        @NotNull Long archiveId,
        @NotBlank String title,
        String subtitle,
        @NotBlank String content,
        String thumbnail,
        @NotBlank String category,
        String teamname
) {}