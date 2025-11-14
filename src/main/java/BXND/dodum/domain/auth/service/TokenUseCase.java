package BXND.dodum.domain.auth.service;

import BXND.dodum.domain.auth.dto.request.GenerateTokenRequest;
import BXND.dodum.domain.auth.dto.request.SignOutRequest;
import BXND.dodum.domain.auth.dto.request.reGenerateTokenRequest;
import BXND.dodum.domain.auth.dto.response.reGenerateAccessTokenResponse;
import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.global.config.RedisConfig;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.security.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenUseCase {
    private final UsersRepository usersRepository;
    private final JwtProvider jwtProvider;
    private final RedisConfig redisConfig;

    public String generateAccessToken(GenerateTokenRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.AccessToken(request.username(), request.role());
        redisConfig.redisTemplate().opsForValue().set("accessToken:" + request.username(), accessToken, 1, TimeUnit.HOURS);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return accessToken;
    }

    public String generateRefreshToken(GenerateTokenRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.RefreshToken(request.username());
        redisConfig.redisTemplate().opsForValue().set("refreshToken:" + request.username(), refreshToken, 7, TimeUnit.DAYS);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return refreshToken;
    }

    public boolean isTokenValid(reGenerateTokenRequest request) {
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String clientRefreshToken = request.refreshToken();
        String savedRefreshToken = valueOperations.get("refreshToken:" + request.username());
        if (savedRefreshToken != null && !savedRefreshToken.isEmpty()) {
            return clientRefreshToken.equals(savedRefreshToken);
        }
        return false;
    }

    public ApiResponse<reGenerateAccessTokenResponse> reGenerateAccessToken(reGenerateTokenRequest request, HttpServletResponse response) {
        if (isTokenValid(request)) {
            Users users = usersRepository.findByUsername(request.username())
                    .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
            GenerateTokenRequest generateTokenRequest = new GenerateTokenRequest(
                    users.getUsername(),
                    users.getRole()
            );
            String newAccessToken = generateAccessToken(generateTokenRequest, response);
            return ApiResponse.ok(new reGenerateAccessTokenResponse(newAccessToken));
        }
        throw new AuthException(AuthStatusCode.INVALID_JWT);
    }

    public void deleteTokens(SignOutRequest request, HttpServletResponse response) {
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String savedAccessToken = valueOperations.get("accessToken:" + request.username());
        String savedRefreshToken = valueOperations.get("refreshToken:" + request.username());
        if (savedRefreshToken == null || savedAccessToken == null) {
            throw new AuthException(AuthStatusCode.INVALID_JWT);
        }
        redisConfig.redisTemplate().delete("accessToken:" + request.username());
        redisConfig.redisTemplate().delete("refreshToken:" + request.username());


        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}
