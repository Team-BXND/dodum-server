package BXND.dodum.domain.file.repository;

import BXND.dodum.domain.file.entity.FileEntityType;
import BXND.dodum.domain.file.entity.FileRecord;
import BXND.dodum.domain.file.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface FileRecordRepository extends JpaRepository<FileRecord, Long> {

    List<FileRecord> findAllByUploaderIdAndStatus(String uploaderId, FileStatus status);

    List<FileRecord> findAllByStatusAndCreatedAtBefore(FileStatus status, OffsetDateTime before);

    List<FileRecord> findAllByStatus(FileStatus status);

    Optional<FileRecord> findByKeyName(String keyName);
    // 특정 엔티티에 ATTACHED 상태인 파일 조회
    List<FileRecord> findAllByEntityTypeAndEntityIdAndStatus(
            FileEntityType entityType,
            String entityId,
            FileStatus status
    );
}
