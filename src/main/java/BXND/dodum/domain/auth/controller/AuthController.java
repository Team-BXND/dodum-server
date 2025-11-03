package BXND.dodum.domain.auth.controller;

import BXND.dodum.domain.auth.dto.request.SignInRequest;
import BXND.dodum.domain.auth.dto.request.SignOutRequest;
import BXND.dodum.domain.auth.dto.request.SignUpRequest;
import BXND.dodum.domain.auth.dto.request.reGenerateTokenRequest;
import BXND.dodum.domain.auth.dto.response.SignInResponse;
import BXND.dodum.domain.auth.dto.response.reGenerateAccessTokenResponse;
import BXND.dodum.domain.auth.service.TokenUseCase;
import BXND.dodum.domain.auth.service.UsersService;
import BXND.dodum.global.data.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UsersService usersService;

    @PostMapping("/signup")
    public ApiResponse<String> SignUp(@RequestBody SignUpRequest request) {
        return usersService.signUp(request);
    }

    @PostMapping("/signin")
    public ApiResponse<SignInResponse> SignIn(@RequestBody SignInRequest request, HttpServletResponse response) {
        return usersService.signIn(request, response);
    }

    @PostMapping("/signout")
    public ApiResponse<String> SignOut(@RequestBody SignOutRequest request) {
        return usersService.signOut(request);
    }

    @PostMapping("/refresh")
    public ApiResponse<reGenerateAccessTokenResponse> reGenerateAccessToken(@RequestBody reGenerateTokenRequest request, HttpServletResponse response) {
        return usersService.refresh(request,response);
    }
}
