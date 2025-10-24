package BXND.dodum.domain.auth.dto.request;

import BXND.dodum.domain.auth.entity.Role;

public record GenerateTokenRequest(
        String username,
        Role role
) {
}
