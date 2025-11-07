package BXND.dodum.domain.file.service;

import BXND.dodum.domain.file.dto.UploadRes;
import BXND.dodum.domain.file.entity.FileRecord;
import BXND.dodum.domain.file.repository.FileRecordRepository;
import BXND.dodum.global.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final StorageService storage;
    private final FileRecordRepository fileRepo;

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
}
