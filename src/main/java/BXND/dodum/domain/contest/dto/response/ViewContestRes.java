package BXND.dodum.domain.contest.dto.response;

import BXND.dodum.domain.contest.entity.Contest;

public record ViewContestRes(
        String title,
        String subTitle,
        String place,
        String phone,
        String email,
        String time,
        String content
) {
    public static ViewContestRes of(Contest contest) {
        return new ViewContestRes(
                contest.getTitle(),
                contest.getSubTitle(),
                contest.getPlace(),
                contest.getPhone(),
                contest.getEmail(),
                contest.getTime(),
                contest.getContent()
        );
    }
}
