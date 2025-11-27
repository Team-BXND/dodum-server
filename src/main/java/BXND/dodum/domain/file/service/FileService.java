package BXND.dodum.domain.file.service;

import BXND.dodum.domain.file.dto.response.UploadRes;
import BXND.dodum.domain.file.entity.FileEntityType;
import BXND.dodum.domain.file.entity.FileRecord;
import BXND.dodum.domain.file.entity.FileStatus;
import BXND.dodum.domain.file.repository.FileRecordRepository;
import BXND.dodum.global.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileService {

    private final StorageService storage;
    private final FileRecordRepository fileRepo;

    // 파일 업로드(생성될 때 pending 상태)
    @Transactional
    public UploadRes upload(MultipartFile file, String uploaderId) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        }
        if (!StringUtils.hasText(uploaderId)) {
            throw new IllegalArgumentException("업로더 정보가 없습니다.");
        }

        String url = storage.upload(file);
        String keyName = url.substring(url.lastIndexOf('/') + 1);

        String originalName = StringUtils.hasText(file.getOriginalFilename())
                ? file.getOriginalFilename()
                : keyName;

        FileRecord saved = fileRepo.save(
                new FileRecord(keyName, originalName, uploaderId, url)
        );

        return UploadRes.builder()
                .id(saved.getId())
                .keyName(saved.getKeyName())
                .originalName(saved.getOriginalName())
                .uploaderId(saved.getUploaderId())
                .url(saved.getUrl())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    // 업로더의 pending 파일을 특정 엔티티에 attach 상태로 연결
    @Transactional
    public void attachFiles(String requesterId, FileEntityType entityType, String entityId, List<Long> fileIds) {
        if (!StringUtils.hasText(requesterId)) throw new IllegalArgumentException("요청자 정보 없음");
        if (entityType == null || !StringUtils.hasText(entityId)) return;
        if (fileIds == null || fileIds.isEmpty()) return;

        Set<Long> keep = new HashSet<>(fileIds);

        List<FileRecord> myPending = fileRepo.findAllByUploaderIdAndStatus(requesterId, FileStatus.PENDING);
        for (FileRecord fr : myPending) {
            if (keep.contains(fr.getId())) {
                fr.attachTo(entityType, entityId);
            }
        }
    }

    // 단일 파일 삭제(실패 시 soft delete 처리)
    @Transactional
    public void deleteOne(Long fileId) {
        FileRecord fr = fileRepo.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));

        try {
            storage.delete(fr.getUrl());
            fileRepo.delete(fr);
        } catch (Exception e) {
            fr.softDelete();
        }
    }

    // 엔티티에 속한 attach 파일 전체 삭제
    @Transactional
    public void deleteAllByEntity(FileEntityType entityType, String entityId) {
        if (entityType == null || !StringUtils.hasText(entityId)) {
            return;
        }

        List<FileRecord> files = fileRepo.findAllByEntityTypeAndEntityIdAndStatus(
                entityType,
                entityId,
                FileStatus.ATTACHED
        );

        for (FileRecord fr : files) {
            try {
                storage.delete(fr.getUrl());
                fileRepo.delete(fr);
            } catch (Exception e) {
                fr.softDelete();
            }
        }
    }

    // 파일 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> serveFile(String keyName) {
        FileRecord fr = fileRepo.findByKeyName(keyName)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));

        if (fr.getStatus() == FileStatus.SOFT_DELETED) {
            throw new IllegalArgumentException("삭제된 파일입니다.");
        }

        Resource resource = storage.loadAsResource(fr.getKeyName());
        String contentType = null;
        try {
            contentType = Files.probeContentType(resource.getFile().toPath());
        } catch (Exception ignore) {}

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
