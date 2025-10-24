package BXND.dodum.domain.auth.dto.response;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {
    public static SignInResponse of(String accessToken, String refreshToken) {
        return new SignInResponse(accessToken, refreshToken);
    }
}
