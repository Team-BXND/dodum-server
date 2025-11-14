package BXND.dodum.domain.contest.dto.request;

public record CreateContestReq(
        String title,
        String subTitle,
        String place,
        String phone,
        String email,
        String time,
        String content
) {
}
