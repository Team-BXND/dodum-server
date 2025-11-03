package BXND.dodum.domain.auth.dto.request;

public record EmailRequest(
        String email,
        String authNum
) {
}
