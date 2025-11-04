package BXND.dodum.domain.archive.controller;

import BXND.dodum.domain.archive.dto.request.*;
import BXND.dodum.domain.archive.dto.response.*;
import BXND.dodum.domain.archive.service.ArchiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService service;

    @PostMapping("/write")
    public StatusIdRes write(@RequestBody @Valid ArchiveWriteReq req) {
        Long id = service.write(req);
        return new StatusIdRes("success", id);
    }

    @GetMapping("/{id}")
    public ArchiveDetailRes detail(@PathVariable Long id) {
        return service.detail(id);
    }

    @GetMapping("/all")
    public List<ArchiveItemRes> list(@RequestParam(name = "category", required = false) String category) {
        return service.list(category);
    }

    @PatchMapping
    public StatusIdRes update(@RequestBody @Valid ArchiveUpdateReq req) {
        Long id = service.update(req);
        return new StatusIdRes("success", id);
    }

    @DeleteMapping
    public StatusMsgRes delete(@RequestBody @Valid ArchiveDeleteReq req) {
        service.delete(req);
        return new StatusMsgRes("success", "deleted");
    }
}
