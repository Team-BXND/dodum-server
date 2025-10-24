package BXND.dodum.domain.auth.dto.request;

public record reGenerateTokenRequest(
        String username,
        String refreshToken
) {
}
