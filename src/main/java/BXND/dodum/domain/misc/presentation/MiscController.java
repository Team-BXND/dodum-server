package BXND.dodum.domain.misc.presentation;

import BXND.dodum.domain.misc.application.MiscApplicationUseCase;
import BXND.dodum.domain.misc.application.data.req.CreateMiscReq;
import BXND.dodum.domain.misc.application.data.req.GetAllMiscReq;
import BXND.dodum.domain.misc.application.data.req.UpdateMiscReq;
import BXND.dodum.domain.misc.application.data.res.CreateMiscRes;
import BXND.dodum.domain.misc.application.data.res.GetAllMiscRes;
import BXND.dodum.domain.misc.application.data.res.GetMiscRes;
import BXND.dodum.domain.misc.application.data.res.UpdateMiscRes;
import BXND.dodum.global.data.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/{id}")
  public ApiResponse<GetMiscRes> getMisc(@PathVariable Long id) {
    return ApiResponse.ok(miscApplicationUseCase.getMisc(id));
  }

  @PostMapping
  public ApiResponse<CreateMiscRes> createMisc(@Valid @RequestBody CreateMiscReq createMiscReq) {
    return ApiResponse.ok(miscApplicationUseCase.createMisc(createMiscReq));
  }

  @PutMapping("/{id}")
  public ApiResponse<UpdateMiscRes> updateMisc(@PathVariable Long id, @Valid @RequestBody UpdateMiscReq updateMiscReq) {
    return ApiResponse.ok(miscApplicationUseCase.updateMisc(id, updateMiscReq));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> deleteMisc(@PathVariable Long id) {
    miscApplicationUseCase.deleteMisc(id);
    return ApiResponse.ok(null);
  }

  @PatchMapping("/{id}/approve")
  public ApiResponse<Void> approveMisc(@PathVariable Long id) {
    miscApplicationUseCase.approveMisc(id);
    return ApiResponse.ok(null);
  }
}
