package BXND.dodum.domain.auth.service;

import BXND.dodum.domain.auth.dto.request.*;
import BXND.dodum.domain.auth.dto.response.SignInResponse;
import BXND.dodum.domain.auth.dto.response.reGenerateAccessTokenResponse;
import BXND.dodum.domain.auth.entity.Role;
import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.global.data.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final TokenUseCase tokenUseCase;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiResponse<String> signUp(SignUpRequest request) {
        if (usersRepository.existsByUsername(request.username()) || usersRepository.existsByEmail(request.email())) {
            throw new AuthException(AuthStatusCode.ALREADY_EXIST_ACCOUNT);
        }
        Role role = (request.grade() == 1) ? Role.STUDENT : Role.SENIOR;

        Users user = Users.builder()
                .username(request.username())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .email(request.email())
                .phone(request.phone())
                .major(request.major())
                .grade(request.grade())
                .class_no(request.class_no())
                .student_no(request.student_no())
                .club(request.club())
                .history(request.history())
                .role(role)
                .build();

        usersRepository.save(user);
        return ApiResponse.ok("회원가입에 성공했습니다.");
    }
    public ApiResponse<SignInResponse> signIn(SignInRequest request, HttpServletResponse response) {
        Users users = usersRepository.findByUsername(request.username())
                .orElseThrow(() -> new AuthException(AuthStatusCode.INVALID_CREDENTIALS));
        if (!bCryptPasswordEncoder.matches(request.password(), users.getPassword())) {
            throw new  AuthException(AuthStatusCode.INVALID_CREDENTIALS);
        }

        GenerateTokenRequest generateTokenRequest = new  GenerateTokenRequest(
                users.getUsername(),
                users.getRole()
        );

        String accessToken = tokenUseCase.generateAccessToken(generateTokenRequest, response);
        String refreshToken =  tokenUseCase.generateRefreshToken(generateTokenRequest, response);

        return ApiResponse.ok(new SignInResponse(accessToken, refreshToken));
    }

    public ApiResponse<String> signOut(SignOutRequest request, HttpServletResponse response) {
        tokenUseCase.deleteTokens(request, response);
        return ApiResponse.ok("로그아웃이 정상적으로 처리되었습니다.");
    }

    public ApiResponse<reGenerateAccessTokenResponse> refresh(reGenerateTokenRequest request, HttpServletResponse response) {
        return tokenUseCase.reGenerateAccessToken(request, response);
    }

    @Transactional
    public ApiResponse<String> changePassword(PasswordReq request) {

        Users users = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException(AuthStatusCode.INVALID_CREDENTIALS));

        users.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
        usersRepository.save(users);
        return ApiResponse.ok("비밀번호가 변경되었습니다.");
    }
}
