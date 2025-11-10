package BXND.dodum.domain.information.controller;

import BXND.dodum.domain.information.dto.request.CommentReq;
import BXND.dodum.domain.information.dto.request.CreateInfoReq;
import BXND.dodum.domain.information.dto.response.CommentRes;
import BXND.dodum.domain.information.dto.response.GetInfoRes;
import BXND.dodum.domain.information.dto.response.ViewInfoRes;
import BXND.dodum.domain.information.service.InfoService;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {
    private final InfoService infoService;

    @GetMapping
    public ApiResponse<List<GetInfoRes>> getAllInformation(@RequestParam(defaultValue = "0") int page) {
        return infoService.getAllInformation(page);
    }

    @PostMapping
    public ApiResponse<String> createInfo(@RequestBody CreateInfoReq request) {
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
        return infoService.viewInformation(id);
    }

    @PostMapping("/{id}/comment")
    public ApiResponse<String> addComment(@PathVariable Long id, @RequestBody CommentReq commentReq) {
        return infoService.InfoComment(id, commentReq);
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<?> isApproved(@PathVariable Long id) {
        return infoService.isApproved(id);
    }

    @PostMapping("/{id}/like")
    public ApiResponse<String> toggleLike(@PathVariable Long id) {
        return infoService.toggleLike(id);
    }
}