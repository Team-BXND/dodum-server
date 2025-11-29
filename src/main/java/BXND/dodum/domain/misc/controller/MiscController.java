package BXND.dodum.domain.misc.controller;

import BXND.dodum.domain.misc.service.MiscApplicationService;
import BXND.dodum.domain.misc.dto.req.CreateMiscReq;
import BXND.dodum.domain.misc.dto.req.GetAllMiscReq;
import BXND.dodum.domain.misc.dto.req.UpdateMiscReq;
import BXND.dodum.domain.misc.dto.res.CreateMiscRes;
import BXND.dodum.domain.misc.dto.res.GetAllMiscRes;
import BXND.dodum.domain.misc.dto.res.GetMiscRes;
import BXND.dodum.domain.misc.dto.res.UpdateMiscRes;
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
  private final MiscApplicationService miscApplicationService;

  @GetMapping
  public ApiResponse<GetAllMiscRes> getAllMisc(GetAllMiscReq getAllMiscReq){
    return ApiResponse.ok(miscApplicationService.getAllMisc(getAllMiscReq));
  }

  @GetMapping("/{id}")
  public ApiResponse<GetMiscRes> getMisc(@PathVariable Long id) {
    return ApiResponse.ok(miscApplicationService.getMisc(id));
  }

  @PostMapping
  public ApiResponse<CreateMiscRes> createMisc(@Valid @RequestBody CreateMiscReq createMiscReq) {
    return ApiResponse.ok(miscApplicationService.createMisc(createMiscReq));
  }

  @PutMapping("/{id}")
  public ApiResponse<UpdateMiscRes> updateMisc(@PathVariable Long id, @Valid @RequestBody UpdateMiscReq updateMiscReq) {
    return ApiResponse.ok(miscApplicationService.updateMisc(id, updateMiscReq));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> deleteMisc(@PathVariable Long id) {
    miscApplicationService.deleteMisc(id);
    return ApiResponse.ok(null);
  }

  @PatchMapping("/{id}/approve")
  public ApiResponse<Void> approveMisc(@PathVariable Long id) {
    miscApplicationService.approveMisc(id);
    return ApiResponse.ok(null);
  }

  @PatchMapping("/{id}/disapprove")
  public ApiResponse<Void> disapproveMisc(@PathVariable Long id) {
    miscApplicationService.disapproveMisc(id);
    return ApiResponse.ok(null);
  }
}
