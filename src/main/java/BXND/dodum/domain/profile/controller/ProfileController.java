package BXND.dodum.domain.profile.controller;

import BXND.dodum.domain.profile.dto.request.UpdateProfileReq;
import BXND.dodum.domain.profile.dto.response.MyPostsRes;
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

    @GetMapping
    public ApiResponse<ProfileRes> getProfile() {
        return profileService.getProfile();
    }

    @PutMapping
    public ApiResponse<String> updateProfile(@RequestBody UpdateProfileReq request) {
        return profileService.updateProfile(request);
    }
}