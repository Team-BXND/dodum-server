package BXND.dodum.domain.file.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class UploadRes {
    private final Long id;
    private final String keyName;
    private final String originalName;
    private final String uploaderId;
    private final String url;
    private final OffsetDateTime createdAt;
}
