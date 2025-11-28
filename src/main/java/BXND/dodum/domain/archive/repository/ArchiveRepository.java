package BXND.dodum.domain.archive.repository;

import BXND.dodum.domain.archive.entity.ArchivePost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArchiveRepository extends JpaRepository<ArchivePost, Long> {
    List<ArchivePost> findByDeletedFalseOrderByCreatedAtDesc();
    List<ArchivePost> findByCategoryAndDeletedFalseOrderByCreatedAtDesc(String category);
    List<ArchivePost> findByAuthorIdAndDeletedFalseOrderByCreatedAtDesc(String authorId);
}
