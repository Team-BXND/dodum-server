package BXND.dodum.domain.majorai.repository;

import BXND.dodum.domain.majorai.entity.MajorAiResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorAiRepository extends JpaRepository<MajorAiResult, Long> {
}
