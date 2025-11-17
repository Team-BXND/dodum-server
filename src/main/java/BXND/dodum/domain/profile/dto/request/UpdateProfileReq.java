package BXND.dodum.domain.profile.dto.request;

import BXND.dodum.domain.auth.entity.Club;

public record UpdateProfileReq(
        int grade,
        int class_no,
        int student_no,
        String phone,
        String email,
        Club club
){
}
