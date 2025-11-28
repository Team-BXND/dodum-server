package BXND.dodum.domain.contest.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.contest.entity.Contest;
import BXND.dodum.domain.contest.entity.ContestAlarm;
import BXND.dodum.domain.contest.exception.ContestException;
import BXND.dodum.domain.contest.exception.ContestStatusCode;
import BXND.dodum.domain.contest.repository.AlarmRepository;
import BXND.dodum.domain.contest.repository.ContestRepository;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmUseCase {
    private final AlarmRepository alarmRepository;
    private final SecurityUtil securityUtil;
    private final ContestRepository contestRepository;

    @Transactional
    public ApiResponse<Boolean> toggleAlarm(Long id) {
        Users user = securityUtil.getUser();

        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));

        return alarmRepository.findByContestAfterAndUser(contest, user)
                .map(existingAlarm -> {
                    alarmRepository.delete(existingAlarm);
                    return ApiResponse.ok(Boolean.FALSE);
                })
                .orElseGet(() -> {
                    ContestAlarm alarm = ContestAlarm.builder()
                            .contest(contest)
                            .user(user)
                            .build();
                    alarmRepository.save(alarm);
                    return ApiResponse.ok(Boolean.TRUE);
                });
    }
}
