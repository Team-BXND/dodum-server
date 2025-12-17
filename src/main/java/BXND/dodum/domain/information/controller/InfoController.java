package BXND.dodum.domain.information.controller;

import BXND.dodum.domain.information.dto.request.CreateInfoReq;
import BXND.dodum.domain.information.dto.response.GetInfoRes;
import BXND.dodum.domain.information.dto.response.ViewInfoRes;
import BXND.dodum.domain.information.service.InfoService;
import BXND.dodum.domain.information.service.LikeUseCase;
import BXND.dodum.global.data.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {
    private final InfoService infoService;
    private final LikeUseCase likeService;

    @GetMapping
    public ApiResponse<List<GetInfoRes>> getAllInformation(@RequestParam(defaultValue = "0") int page) {
        return infoService.getTrueAllInfo(page);
    }

    @GetMapping("/false")
    public ApiResponse<List<GetInfoRes>> getFalseInformation(@RequestParam(defaultValue = "0") int page) {
        return infoService.getFalseAllInfo(page);
    }

    @PostMapping
    public ApiResponse<String> createInfo(@Valid @RequestBody CreateInfoReq request) {
        return infoService.createInfo(request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteInfo(@PathVariable Long id) {
        return infoService.deleteInfo(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateInfo(@PathVariable Long id, @RequestBody CreateInfoReq request) {
        return infoService.updateInfo(id, request);
    }

    @GetMapping("/{id}")
    public ApiResponse<ViewInfoRes> viewInfo(@PathVariable Long id) {
        return infoService.viewInfo(id);
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<?> approveInfo(@PathVariable Long id) {
        return infoService.approveInfo(id);
    }

    @PostMapping("/{id}/like")
    public ApiResponse<String> toggleLike(@PathVariable Long id) {
        return likeService.toggleLike(id);
    }
}