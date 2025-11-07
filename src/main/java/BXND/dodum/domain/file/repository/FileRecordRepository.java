package BXND.dodum.domain.file.repository;

import BXND.dodum.domain.file.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRecordRepository extends JpaRepository<FileRecord, Long> {}
