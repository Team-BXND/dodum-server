package BXND.dodum.domain.file.controller;

import BXND.dodum.domain.file.dto.UploadRes;
import BXND.dodum.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public UploadRes upload(@RequestPart("file") MultipartFile file, Authentication auth) {
        return fileService.upload(file, auth.getName());
    }
}