package BXND.dodum.domain.profile.dto.request;

public record UpdateProfileReq(
        int grade,
        int class_no,
        int student_no,
        String phone,
        String email,
        String club
){
}
