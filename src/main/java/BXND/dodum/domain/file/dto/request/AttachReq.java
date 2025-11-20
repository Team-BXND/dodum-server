package BXND.dodum.domain.file.dto.request;

import lombok.Getter;
import java.util.List;

@Getter
public class AttachReq {
    private String entityType;
    private String entityId;
    private List<Long> fileIds;
}
