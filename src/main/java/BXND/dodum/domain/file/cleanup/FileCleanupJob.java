package BXND.dodum.domain.file.cleanup;

import BXND.dodum.domain.file.entity.FileRecord;
import BXND.dodum.domain.file.entity.FileStatus;
import BXND.dodum.domain.file.repository.FileRecordRepository;
import BXND.dodum.global.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileCleanupJob {

    private final FileRecordRepository fileRepo;
    private final StorageService storage;

    @Scheduled(cron = "0 0 */12 * * *")
    @Transactional
    public void cleanup() {
        log.info(">>> FileCleanupJob 실행됨");

        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime pendingGrace = now.minusSeconds(10);

        List<FileRecord> expiredPending =
                fileRepo.findAllByStatusAndCreatedAtBefore(FileStatus.PENDING, pendingGrace);

        List<FileRecord> softDeleted =
                fileRepo.findAllByStatus(FileStatus.SOFT_DELETED);

        Stream.concat(expiredPending.stream(), softDeleted.stream())
                .distinct()
                .forEach(fr -> {
                    try {
                        log.info("삭제 대상 파일: id={}, url={}", fr.getId(), fr.getUrl());
                        storage.delete(fr.getUrl());
                        fileRepo.delete(fr);
                        log.info("삭제 성공: id={}", fr.getId());
                    } catch (Exception e) {
                        log.warn("파일 삭제 실패: {}", fr.getUrl(), e);
                    }
                });
    }
}
