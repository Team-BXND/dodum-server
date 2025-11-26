package BXND.dodum.domain.contest.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.contest.entity.Contest;
import BXND.dodum.domain.contest.entity.ContestAlarm;
import BXND.dodum.domain.contest.exception.ContestException;
import BXND.dodum.domain.contest.exception.ContestStatusCode;
import BXND.dodum.domain.contest.repository.AlarmRepository;
import BXND.dodum.domain.contest.repository.ContestRepository;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmUseCase {
    private final AlarmRepository alarmRepository;
    private final UsersRepository usersRepository;
    private final ContestRepository contestRepository;

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
    }

    @Transactional
    public ApiResponse<Boolean> toggleAlarm(Long id) {
        Users user = getUser();

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
