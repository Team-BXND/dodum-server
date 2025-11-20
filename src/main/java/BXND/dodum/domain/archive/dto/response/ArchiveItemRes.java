package BXND.dodum.domain.archive.dto.response;

import BXND.dodum.domain.archive.entity.ArchivePost;
import java.time.Instant;

//리스트 조회 응답
public record ArchiveItemRes(
        Long id,
        String title,
        String subtitle,
        String teamname,
        String content,
        String category,
        Instant createdAt
) {
    public static ArchiveItemRes from(ArchivePost p) {
        return new ArchiveItemRes(
                p.getId(),
                p.getTitle(),
                p.getSubtitle(),
                p.getTeamname(),
                p.getContent(),
                p.getCategory(),
                p.getCreatedAt()
        );
    }
}
