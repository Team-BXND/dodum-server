package BXND.dodum.domain.misc.application.data.req;

import BXND.dodum.domain.misc.application.data.MiscCategoryE;
import BXND.dodum.domain.misc.application.data.MiscCriteriaE;

public record GetAllMiscReq(
    MiscCategoryE category,
    MiscCriteriaE criteria,
    Integer page
) {}
