package BXND.dodum.domain.information.repository;

import BXND.dodum.domain.information.entity.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Page<Info> findAllByIsApprovedTrue(Pageable pageable);
    Page<Info> findAllByIsApprovedTrueAndTitle(String title, Pageable pageable);

    Optional<Info> findByIdAndIsApprovedTrue(Long id);
}
