package BXND.dodum.domain.misc.dto.res;

import BXND.dodum.domain.misc.entity.MiscInfo;
import java.util.List;

public record GetAllMiscRes(
   List<GetMiscRes> infos
) {
    public static GetAllMiscRes from(List<MiscInfo> miscInfos) {
        List<GetMiscRes> infoDtos = miscInfos.stream()
            .map(GetMiscRes::from)
            .toList();
        return new GetAllMiscRes(infoDtos);
    }
}
