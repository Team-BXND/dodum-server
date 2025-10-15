package BXND.dodum.domain.misc.application.data.res;

import BXND.dodum.domain.misc.application.entity.MiscInfo;
import java.util.List;

public record GetAllMiscRes(
   List<MiscInfo> infos
) {}
