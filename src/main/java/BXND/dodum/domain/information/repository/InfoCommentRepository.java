package BXND.dodum.domain.information.repository;

import BXND.dodum.domain.information.entity.InfoComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoCommentRepository extends JpaRepository<InfoComment, Long> {

}
