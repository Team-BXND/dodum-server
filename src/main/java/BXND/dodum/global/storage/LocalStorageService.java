package BXND.dodum.global.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${storage.local.dir}")
    private String uploadDir;

    @Value("${storage.local.base-url}")
    private String baseUrl;

    @Override
    public String upload(MultipartFile file) {
        try {
            Path root = Path.of(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(root);

            String original = file.getOriginalFilename();
            String ext = getExt(original);
            String key = UUID.randomUUID() + (ext == null ? "" : "." + ext.toLowerCase());

            Path dest = root.resolve(key).normalize();
            if (!dest.startsWith(root)) throw new SecurityException("잘못된 경로");

            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            return baseUrl + key;
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    @Override
    public void delete(String url) {
        if (url == null) return;
        String filename = url.substring(url.lastIndexOf('/') + 1);
        try {
            Path root = Path.of(uploadDir).toAbsolutePath().normalize();
            Path target = root.resolve(filename).normalize();
            if (!target.startsWith(root)) return;

            boolean deleted = Files.deleteIfExists(target);
            log.info("delete {} -> {}", deleted, target);
        } catch (IOException ignore) {}
    }

    // 확장자 뽑아내는 메서드
    private String getExt(String name) {
        if (name == null) return null;
        int i = name.lastIndexOf('.');
        return i < 0 ? null : name.substring(i + 1);
    }

    // 디렉토리에서 keyName 파일을 찾아 Resource로 만들어 반환
    @Override
    public Resource loadAsResource(String keyName) {
        try {
            Path root = Path.of(uploadDir).toAbsolutePath().normalize();
            Path filePath = root.resolve(keyName).normalize();
            if (!filePath.startsWith(root)) {
                throw new SecurityException("잘못된 경로 접근: " + keyName);
            }
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new RuntimeException("파일을 읽을 수 없습니다: " + keyName);
        } catch (Exception e) {
            throw new RuntimeException("파일 로드 실패: " + keyName, e);
        }
    }
}
