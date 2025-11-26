package BXND.dodum.domain.misc.application.data.res;

import BXND.dodum.domain.misc.application.data.MiscCategoryE;
import BXND.dodum.domain.misc.application.entity.MiscInfo;

public record GetMiscRes(
    Long id,
    String title,
    String content,
    int likes,
    MiscCategoryE category,
    boolean isApproved
) {
    public static GetMiscRes from(MiscInfo miscInfo) {
        return new GetMiscRes(
            miscInfo.getId(),
            miscInfo.getTitle(),
            miscInfo.getContent(),
            miscInfo.getLikes(),
            miscInfo.getCategory(),
            miscInfo.isApproved()
        );
    }
}