package BXND.dodum.domain.profile.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.profile.dto.request.UpdateProfileReq;
import BXND.dodum.domain.profile.dto.response.ProfileRes;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UsersRepository usersRepository;


    public Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
    }

    public ApiResponse<ProfileRes> getProfile() {
        Users user = getUser();
        return ApiResponse.ok(ProfileRes.of(user));
    }


    public ApiResponse<String> updateProfile(UpdateProfileReq request) {
        Users user = getUser();
        user.updateProfile(request);
        usersRepository.save(user);
        return ApiResponse.ok("프로필이 수정되었습니다.");
    }
}
