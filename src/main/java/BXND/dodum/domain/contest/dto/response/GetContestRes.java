package BXND.dodum.domain.contest.dto.response;

public record GetContestRes(
        Long id,
        String title,
        String subTitle,
        String place,
        String phone,
        String email,
        String time
) {
}
