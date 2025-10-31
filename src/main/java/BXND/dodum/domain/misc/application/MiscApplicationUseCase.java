package BXND.dodum.domain.misc.application;

import BXND.dodum.domain.misc.application.data.MiscCriteriaE;
import BXND.dodum.domain.misc.application.data.req.ApproveMiscReq;
import BXND.dodum.domain.misc.application.data.req.CreateMiscReq;
import BXND.dodum.domain.misc.application.data.req.DeleteMiscReq;
import BXND.dodum.domain.misc.application.data.req.GetAllMiscReq;
import BXND.dodum.domain.misc.application.data.req.GetMiscReq;
import BXND.dodum.domain.misc.application.data.req.UpdateMiscReq;
import BXND.dodum.domain.misc.application.data.res.CreateMiscRes;
import BXND.dodum.domain.misc.application.data.res.GetAllMiscRes;
import BXND.dodum.domain.misc.application.data.res.GetMiscRes;
import BXND.dodum.domain.misc.application.data.res.UpdateMiscRes;
import BXND.dodum.domain.misc.application.entity.MiscInfo;
import BXND.dodum.domain.misc.application.repository.MiscInfoRepository;
import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.CommonStatusCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MiscApplicationUseCase {
  private final MiscInfoRepository miscInfoRepository;

  public GetAllMiscRes getAllMisc(GetAllMiscReq getAllMiscReq){
    Sort.Direction criteria = getAllMiscReq.criteria().name().equals(MiscCriteriaE.LATEST.name()) ? Direction.DESC : Direction.ASC;
    Pageable pageable = PageRequest.of(getAllMiscReq.page(), 10, Sort.by(criteria, "createdAt"));
    Page<MiscInfo> infosPage = miscInfoRepository.findAllByIsApprovedTrue(pageable);
    return new GetAllMiscRes(infosPage.stream().toList());
  }

  public GetMiscRes getMisc(Long id) {
    MiscInfo miscInfo = miscInfoRepository.findByIdAndIsApprovedTrue(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));
    return GetMiscRes.from(miscInfo);
  }

  @Transactional
  public CreateMiscRes createMisc(CreateMiscReq createMiscReq) {
    MiscInfo miscInfo = MiscInfo.builder()
        .title(createMiscReq.title())
        .content(createMiscReq.content())
        .likes(0)
        .build();
    
    MiscInfo savedMiscInfo = miscInfoRepository.save(miscInfo);
    return CreateMiscRes.from(savedMiscInfo);
  }

  @Transactional
  public UpdateMiscRes updateMisc(Long id, UpdateMiscReq updateMiscReq) {
    if (!id.equals(updateMiscReq.id())) {
      throw new ApplicationException(CommonStatusCode.BAD_REQUEST);
    }
    
    MiscInfo miscInfo = miscInfoRepository.findById(updateMiscReq.id())
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));

    // To-Do: 유저 권한 확인 필요

    miscInfo.setTitle(updateMiscReq.title());
    miscInfo.setContent(updateMiscReq.content());
    
    return UpdateMiscRes.from(miscInfo);
  }

  @Transactional
  public void deleteMisc(Long id) {
    MiscInfo miscInfo = miscInfoRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));

    // To-Do: 유저 권한 확인 필요

    miscInfoRepository.delete(miscInfo);
  }

  @Transactional
  public void approveMisc(Long id) {
    MiscInfo miscInfo = miscInfoRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));

    // To-Do: 유저 권한 확인 필요

    miscInfo.setApproved(true);
  }
}
