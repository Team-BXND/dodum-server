package BXND.dodum.domain.contest.repository;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.contest.entity.Contest;
import BXND.dodum.domain.contest.entity.ContestAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<ContestAlarm, Long> {
    Optional<ContestAlarm> findByContestAfterAndUser(Contest contestAfter, Users user);
}
