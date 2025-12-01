package BXND.dodum.domain.misc.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.misc.dto.req.CreateMiscReq;
import BXND.dodum.domain.misc.dto.req.GetAllMiscReq;
import BXND.dodum.domain.misc.dto.req.UpdateMiscReq;
import BXND.dodum.domain.misc.dto.res.CreateMiscRes;
import BXND.dodum.domain.misc.dto.res.GetAllMiscRes;
import BXND.dodum.domain.misc.dto.res.GetMiscRes;
import BXND.dodum.domain.misc.dto.res.UpdateMiscRes;
import BXND.dodum.domain.misc.entity.MiscInfo;
import BXND.dodum.domain.misc.repository.MiscInfoRepository;
import BXND.dodum.global.exception.exception.ApplicationException;
import BXND.dodum.global.exception.status_code.CommonStatusCode;
import BXND.dodum.global.util.SecurityUtil;
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
public class MiscApplicationService {
  private final SecurityUtil securityUtil;
  private final MiscInfoRepository miscInfoRepository;

  public GetAllMiscRes getAllMisc(GetAllMiscReq getAllMiscReq) {
    if (getAllMiscReq.criteria() == null) {
      throw new ApplicationException(CommonStatusCode.BAD_REQUEST);
    }
    String sortProperty;
    Sort.Direction sortDirection;
    switch (getAllMiscReq.criteria()) {
      case LATEST:
        sortProperty = "createdAt";
        sortDirection = Direction.DESC;
        break;
      case LIKES:
        sortProperty = "likes";
        sortDirection = Direction.DESC;
        break;
      default:
        throw new ApplicationException(CommonStatusCode.BAD_REQUEST);
    }
    int page = Optional.ofNullable(getAllMiscReq.page()).orElse(0);
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sortDirection, sortProperty));
    Page<MiscInfo> infosPage = miscInfoRepository.findAllByIsApprovedTrue(pageable);
    return GetAllMiscRes.from(infosPage.stream().toList());
  }

  public GetMiscRes getMisc(Long id) {
    MiscInfo miscInfo = miscInfoRepository.findByIdAndIsApprovedTrue(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));
    return GetMiscRes.from(miscInfo);
  }

  @Transactional
  public CreateMiscRes createMisc(CreateMiscReq createMiscReq) {
    Users user = securityUtil.getUser();
    MiscInfo miscInfo = MiscInfo.builder()
        .title(createMiscReq.title())
        .content(createMiscReq.content())
        .category(createMiscReq.category())
        .author(user)
        .likes(0)
        .build();
    
    MiscInfo savedMiscInfo = miscInfoRepository.save(miscInfo);
    return CreateMiscRes.from(savedMiscInfo);
  }

  @Transactional
  public UpdateMiscRes updateMisc(Long id, UpdateMiscReq updateMiscReq) {
    MiscInfo miscInfo = miscInfoRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));

    // To-Do: 유저 권한 확인 필요

    miscInfo.setTitle(updateMiscReq.title());
    miscInfo.setContent(updateMiscReq.content());
    miscInfo.setCategory(updateMiscReq.category());
    
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

  @Transactional
  public void disapproveMisc(Long id) {
    MiscInfo miscInfo = miscInfoRepository.findById(id)
        .orElseThrow(() -> new ApplicationException(CommonStatusCode.NOT_FOUND));

    // To-Do: 유저 권한 확인 필요

    miscInfo.setApproved(false);
  }
}
