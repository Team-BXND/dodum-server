package BXND.dodum.domain.information.repository;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.information.entity.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Page<Info> findAllByIsApprovedTrue(Pageable pageable);

    Optional<Info> findByIdAndIsApprovedTrue(Long id);

    Page<Info> findAllByIsApprovedFalse(Pageable pageable);

    List<Info> findByAuthor(Users author);
}
