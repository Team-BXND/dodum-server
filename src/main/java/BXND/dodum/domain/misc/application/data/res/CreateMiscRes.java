package BXND.dodum.domain.misc.application.data.res;

import BXND.dodum.domain.misc.application.entity.MiscInfo;

public record CreateMiscRes(
    Long id,
    String title,
    String content,
    int likes,
    boolean isApproved
) {
    public static CreateMiscRes from(MiscInfo miscInfo) {
        return new CreateMiscRes(
            miscInfo.getId(),
            miscInfo.getTitle(),
            miscInfo.getContent(),
            miscInfo.getLikes(),
            miscInfo.isApproved()
        );
    }
}