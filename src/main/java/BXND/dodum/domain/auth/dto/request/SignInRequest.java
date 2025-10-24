package BXND.dodum.domain.auth.dto.request;

public record SignInRequest(
        String username,
        String password
) {
}
