package BXND.dodum.domain.profile.dto.response;

import BXND.dodum.domain.archive.dto.response.ArchiveItemRes;
import BXND.dodum.domain.contest.dto.response.GetContestRes;
import BXND.dodum.domain.information.dto.response.GetInfoRes;

import java.util.List;

public record MyPostsRes(
        List<GetInfoRes> informations,
        List<GetContestRes> contests,
        List<ArchiveItemRes> archives
) {
}