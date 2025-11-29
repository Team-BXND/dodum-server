package BXND.dodum.domain.misc.repository;

import BXND.dodum.domain.misc.entity.MiscInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiscInfoRepository extends JpaRepository<MiscInfo, Long> {
  Page<MiscInfo> findAllByIsApprovedTrue(Pageable pageable);
  Optional<MiscInfo> findByIdAndIsApprovedTrue(Long id);
}
