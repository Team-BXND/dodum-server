package BXND.dodum.domain.misc.dto.res;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.misc.dto.MiscCategoryE;
import BXND.dodum.domain.misc.entity.MiscInfo;
import BXND.dodum.domain.profile.dto.response.ProfileRes;

public record GetMiscRes(
    Long id,
    String title,
    String content,
    int likes,
    MiscCategoryE category,
    boolean isApproved,
    ProfileRes author
) {
    public static GetMiscRes from(MiscInfo miscInfo) {
        return new GetMiscRes(
            miscInfo.getId(),
            miscInfo.getTitle(),
            miscInfo.getContent(),
            miscInfo.getLikes(),
            miscInfo.getCategory(),
            miscInfo.isApproved(),
            ProfileRes.of(miscInfo.getAuthor())
        );
    }
}