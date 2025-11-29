package BXND.dodum.domain.misc.dto.req;

import BXND.dodum.domain.misc.dto.MiscCategoryE;
import BXND.dodum.domain.misc.dto.MiscCriteriaE;

public record GetAllMiscReq(
    MiscCategoryE category,
    MiscCriteriaE criteria,
    Integer page
) {}
