package BXND.dodum.domain.file;

import BXND.dodum.global.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileUploadController {

    private final StorageService storage;

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public UploadRes upload(@RequestPart("file") MultipartFile file) {
        String url = storage.upload(file);
        return new UploadRes("OK", url);
    }

    public record UploadRes(String status, String url) {}
}
