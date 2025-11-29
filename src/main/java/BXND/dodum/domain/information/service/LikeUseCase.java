package BXND.dodum.domain.information.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.information.entity.Info;
import BXND.dodum.domain.information.entity.InfoLike;
import BXND.dodum.domain.information.exception.InfoException;
import BXND.dodum.domain.information.exception.InfoStatusCode;
import BXND.dodum.domain.information.repository.InfoLikeRepository;
import BXND.dodum.domain.information.repository.InfoRepository;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeUseCase {

    private final InfoRepository infoRepository;
    private final InfoLikeRepository infoLikeRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public ApiResponse<String> toggleLike(Long id) {
        Users user = securityUtil.getUser();

        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));

        return infoLikeRepository.findByInfoAndUser(info, user)
                .map(existingLike -> {
                    infoLikeRepository.delete(existingLike);
                    return ApiResponse.ok("좋아요를 취소했습니다.");
                })
                .orElseGet(() -> {
                    InfoLike newLike = InfoLike.builder()
                            .info(info)
                            .user(user)
                            .build();
                    infoLikeRepository.save(newLike);
                    return ApiResponse.ok("좋아요를 눌렀습니다.");
                });
    }
}