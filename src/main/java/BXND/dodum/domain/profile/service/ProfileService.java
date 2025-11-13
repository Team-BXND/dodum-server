package BXND.dodum.domain.profile.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.information.repository.InfoCommentRepository;
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
    private final InfoCommentRepository infoCommentRepository;

    public ApiResponse<ProfileRes> getProfile(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        Long countComment = infoCommentRepository.countByAuthorId(id);

        return ApiResponse.ok(ProfileRes.of(user, countComment));
    }


    public ApiResponse<String> updateProfile(Long id, UpdateProfileReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        if (!auth.getName().equals(user.getUsername())) {
            throw new AuthException(AuthStatusCode.ACCESS_DENIED);
        }
        user.updateProfile(request);
        usersRepository.save(user);
        return ApiResponse.ok("프로필이 수정되었습니다.");
    }
    // 추후 개발 예정
}
