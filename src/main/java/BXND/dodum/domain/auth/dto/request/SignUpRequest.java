package BXND.dodum.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        String email,
        @NotNull
        String phone,
        String major,
        int grade,
        int class_no,
        int student_no,
        String club,
        String history

) {
}
