package BXND.dodum.domain.contest.repository;

import BXND.dodum.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
