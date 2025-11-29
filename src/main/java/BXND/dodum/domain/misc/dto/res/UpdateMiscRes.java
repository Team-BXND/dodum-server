package BXND.dodum.domain.misc.dto.res;

import BXND.dodum.domain.misc.entity.MiscInfo;

public record UpdateMiscRes(
    Long id,
    String title,
    String content,
    int likes,
    boolean isApproved
) {
    public static UpdateMiscRes from(MiscInfo miscInfo) {
        return new UpdateMiscRes(
            miscInfo.getId(),
            miscInfo.getTitle(),
            miscInfo.getContent(),
            miscInfo.getLikes(),
            miscInfo.isApproved()
        );
    }
}