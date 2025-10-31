package BXND.dodum.domain.misc.application.data.res;

import BXND.dodum.domain.misc.application.entity.MiscInfo;

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