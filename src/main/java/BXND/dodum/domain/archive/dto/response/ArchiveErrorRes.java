package BXND.dodum.domain.archive.dto.response;

import java.time.Instant;
import java.util.List;

public record ArchiveErrorRes(
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        List<FieldError> errors
) {
    public static ArchiveErrorRes of(int status, String code, String message, String path, List<FieldError> errors) {
        return new ArchiveErrorRes(Instant.now(), status, code, message, path, errors);
    }

    public record FieldError(String field, String value, String reason) {}
}
