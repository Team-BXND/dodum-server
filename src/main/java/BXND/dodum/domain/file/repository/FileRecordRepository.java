package BXND.dodum.domain.file.repository;

import BXND.dodum.domain.file.entity.FileRecord;
import BXND.dodum.domain.file.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.OffsetDateTime;
import java.util.List;

public interface FileRecordRepository extends JpaRepository<FileRecord, Long> {
    List<FileRecord> findAllByUploaderIdAndStatus(String uploaderId, FileStatus status);
    List<FileRecord> findAllByStatusAndCreatedAtBefore(FileStatus status, OffsetDateTime before);
    List<FileRecord> findAllByStatus(FileStatus status);
    Optional<FileRecord> findByKeyName(String keyName);
}