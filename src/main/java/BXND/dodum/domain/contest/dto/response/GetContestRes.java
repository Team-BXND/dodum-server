package BXND.dodum.domain.contest.dto.response;

import BXND.dodum.domain.contest.entity.Contest;

import java.util.List;
import java.util.stream.Collectors;

public record GetContestRes(
        Long id,
        String title,
        String subTitle,
        String place,
        String phone,
        String email,
        String time
) {
    public static GetContestRes of(Contest contest) {
        return new GetContestRes(
                contest.getId(),
                contest.getTitle(),
                contest.getSubTitle(),
                contest.getPlace(),
                contest.getPhone(),
                contest.getEmail(),
                contest.getTime()
        );
    }

    public static List<GetContestRes> ofList(List<Contest> contests) {
        return contests.stream()
                .map(GetContestRes::of)
                .collect(Collectors.toList());
    }
}
