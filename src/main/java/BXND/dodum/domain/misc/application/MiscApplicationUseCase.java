package BXND.dodum.domain.misc.application;

import BXND.dodum.domain.misc.application.data.MiscCriteriaE;
import BXND.dodum.domain.misc.application.data.req.GetAllMiscReq;
import BXND.dodum.domain.misc.application.data.res.GetAllMiscRes;
import BXND.dodum.domain.misc.application.entity.MiscInfo;
import BXND.dodum.domain.misc.application.repository.MiscInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MiscApplicationUseCase {
  private final MiscInfoRepository miscInfoRepository;

  public GetAllMiscRes getAllMisc(GetAllMiscReq getAllMiscReq){
    Sort.Direction criteria = getAllMiscReq.criteria().name().equals(MiscCriteriaE.LATEST.name()) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(getAllMiscReq.page(), 10, Sort.by(criteria, "createdAt"));
    Page<MiscInfo> infosPage = miscInfoRepository.findAll(pageable);
    return new GetAllMiscRes(infosPage.stream().toList());
  }
}
