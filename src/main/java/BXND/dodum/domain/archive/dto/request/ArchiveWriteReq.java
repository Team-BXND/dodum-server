package BXND.dodum.domain.archive.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArchiveWriteReq(
        @NotBlank String title,
        String subtitle,
        @NotBlank String content,
        String thumbnail,
        @NotBlank String category,
        String teamname
) {}