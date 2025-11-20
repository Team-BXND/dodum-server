package BXND.dodum.domain.profile.controller;

import BXND.dodum.domain.profile.dto.request.UpdateProfileReq;
import BXND.dodum.domain.profile.dto.response.ProfileRes;
import BXND.dodum.domain.profile.service.ProfileService;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ApiResponse<ProfileRes> getProfile(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateProfile(@PathVariable Long id,@RequestBody UpdateProfileReq request) {
        return profileService.updateProfile(id, request);
    }
}