package BXND.dodum.domain.file.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileDownloadRes {
    private String originalName;
    private String contentType;
    private Resource resource;
}