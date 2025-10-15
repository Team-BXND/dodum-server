package BXND.dodum.domain.misc.presentation;

import BXND.dodum.domain.misc.application.MiscApplicationUseCase;
import BXND.dodum.domain.misc.application.data.req.GetAllMiscReq;
import BXND.dodum.domain.misc.application.data.res.GetAllMiscRes;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/misc")
@RequiredArgsConstructor
public class MiscController {
  private final MiscApplicationUseCase miscApplicationUseCase;

  @GetMapping
  public ApiResponse<GetAllMiscRes> getAllMisc(GetAllMiscReq getAllMiscReq){
    return ApiResponse.ok(miscApplicationUseCase.getAllMisc(getAllMiscReq));
  }
}
