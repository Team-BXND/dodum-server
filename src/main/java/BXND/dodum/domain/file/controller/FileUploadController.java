package BXND.dodum.domain.file.controller;

import BXND.dodum.domain.file.dto.request.AttachReq;
import BXND.dodum.domain.file.dto.response.UploadRes;
import BXND.dodum.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/attach")
    @PreAuthorize("isAuthenticated()")
    public void attach(@RequestBody AttachReq req, Authentication auth) {
        fileService.attachFiles(auth.getName(), req.getEntityType(), req.getEntityId(), req.getFileIds());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteOne(@PathVariable Long id) {
        fileService.deleteOne(id);
    }

    @GetMapping("/{keyName}")
    public ResponseEntity<Resource> getFile(@PathVariable String keyName) {
        return fileService.serveFile(keyName);
    }
}
