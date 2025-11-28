package BXND.dodum.domain.archive.service;

import BXND.dodum.domain.archive.dto.request.*;
import BXND.dodum.domain.archive.dto.response.*;
import BXND.dodum.domain.archive.entity.ArchivePost;
import BXND.dodum.domain.archive.exception.ArchiveException;
import BXND.dodum.domain.archive.exception.ArchiveStatusCode;
import BXND.dodum.domain.archive.repository.ArchiveRepository;
import BXND.dodum.domain.file.entity.FileEntityType;
import BXND.dodum.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchiveService {

    private final ArchiveRepository repo;
    private final FileService fileService;

    private String currentUserId() { // 사용자 id가져오는거
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            throw new ArchiveException(ArchiveStatusCode.UNAUTHENTICATED);
        }
        return auth.getName();
    }

    private boolean hasAnyRole(String... roles) { // 권환 가지고있는지 검사
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;
        for (GrantedAuthority ga : auth.getAuthorities()) {
            for (String r : roles) {
                if (r.equals(ga.getAuthority())) return true;
            }
        }
        return false;
    }

    private void assertCanModify(ArchivePost p) { // 수정 삭제관련 권환확인
        String me = currentUserId();
        boolean owner = me.equals(p.getAuthorId());
        boolean admin = hasAnyRole("ROLE_ADMIN", "ROLE_TEACHER");
        if (!(owner || admin)) throw new ArchiveException(ArchiveStatusCode.FORBIDDEN);
    }

    @Transactional
    public Long write(ArchiveWriteReq r) {
        String username = currentUserId();

        ArchivePost p = ArchivePost.builder()
                .authorId(username)
                .title(r.title())
                .subtitle(r.subtitle())
                .content(r.content())
                .thumbnailUrl(r.thumbnail())
                .category(r.category())
                .teamname((r.teamname() == null || r.teamname().isBlank()) ? null : r.teamname().trim())
                .deleted(false)
                .build();

        return repo.save(p).getId();
    }

    public ArchiveDetailRes detail(Long id) {
        ArchivePost p = repo.findById(id)
                .orElseThrow(() -> new ArchiveException(ArchiveStatusCode.NOT_FOUND));
        if (p.isDeleted()) throw new ArchiveException(ArchiveStatusCode.NOT_FOUND);
        return ArchiveDetailRes.from(p);
    }

    public List<ArchiveItemRes> list(String category) {
        String c = (category == null) ? null : category.trim();
        List<ArchivePost> posts = (c == null || c.isEmpty())
                ? repo.findByDeletedFalseOrderByCreatedAtDesc()
                : repo.findByCategoryAndDeletedFalseOrderByCreatedAtDesc(c);

        return posts.stream().map(ArchiveItemRes::from).toList();
    }

    @Transactional
    public Long update(ArchiveUpdateReq r) {
        ArchivePost p = repo.findById(r.archiveId())
                .orElseThrow(() -> new ArchiveException(ArchiveStatusCode.NOT_FOUND));
        if (p.isDeleted()) throw new ArchiveException(ArchiveStatusCode.FORBIDDEN, "삭제된 글은 수정할 수 없습니다.");
        assertCanModify(p);

        p.update(
                r.title(),
                r.subtitle(),
                r.content(),
                r.thumbnail(),
                r.category(),
                (r.teamname() == null || r.teamname().isBlank()) ? null : r.teamname().trim()
        );
        return p.getId();
    }

    @Transactional
    public void delete(ArchiveDeleteReq r) {
        ArchivePost p = repo.findById(r.archiveId())
                .orElseThrow(() -> new ArchiveException(ArchiveStatusCode.NOT_FOUND));
        if (p.isDeleted()) return;
        assertCanModify(p);

        fileService.deleteAllByEntity(FileEntityType.ARCHIVE, String.valueOf(p.getId()));

        p.setDeleted(true);
        p.setDeletedAt(Instant.now());
    }
}
