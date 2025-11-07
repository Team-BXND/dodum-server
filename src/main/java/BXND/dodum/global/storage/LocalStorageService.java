package BXND.dodum.global.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

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
            Files.createDirectories(Path.of(uploadDir));

            String original = file.getOriginalFilename();
            String ext = getExt(original);
            String key = UUID.randomUUID() + (ext == null ? "" : "." + ext);

            Path dest = Path.of(uploadDir).resolve(key);

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
            Files.deleteIfExists(Path.of(uploadDir).resolve(filename));
        } catch (IOException ignore) {}
    }

    private String getExt(String name) {
        if (name == null) return null;
        int i = name.lastIndexOf('.');
        return i < 0 ? null : name.substring(i + 1);
    }
}
