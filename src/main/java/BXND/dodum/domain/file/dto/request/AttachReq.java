package BXND.dodum.domain.file.dto.request;

import BXND.dodum.domain.file.entity.FileEntityType;
import lombok.Getter;
import java.util.List;

@Getter
public class AttachReq {
    private FileEntityType entityType;
    private String entityId;
    private List<Long> fileIds;
}
