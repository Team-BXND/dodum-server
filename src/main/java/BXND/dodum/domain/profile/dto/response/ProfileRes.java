package BXND.dodum.domain.profile.dto.response;

import BXND.dodum.domain.auth.entity.Club;
import BXND.dodum.domain.auth.entity.Users;

public record ProfileRes(
        String username,
        String phone,
        String email,
        int grade,
        int class_no,
        int student_no,
        Club club
) {
    public static ProfileRes of(Users user) {
        return new ProfileRes(
                user.getUsername(),
                user.getPhone(),
                user.getEmail(),
                user.getGrade(),
                user.getClass_no(),
                user.getStudent_no(),
                user.getClub()
        );
    }
}
