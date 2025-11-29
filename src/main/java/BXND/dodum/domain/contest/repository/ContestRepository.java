package BXND.dodum.domain.contest.repository;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findByAuthor(Users author);
}
