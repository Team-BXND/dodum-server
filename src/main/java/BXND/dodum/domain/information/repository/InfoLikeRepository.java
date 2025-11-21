package BXND.dodum.domain.information.repository;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.information.entity.Info;
import BXND.dodum.domain.information.entity.InfoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoLikeRepository extends JpaRepository<InfoLike, Long> {
    Optional<InfoLike> findByInfoAndUser(Info info, Users user);
}
