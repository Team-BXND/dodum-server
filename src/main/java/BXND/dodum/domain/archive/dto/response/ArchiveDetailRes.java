package BXND.dodum.domain.archive.dto.response;

import BXND.dodum.domain.archive.entity.ArchivePost;
import java.time.Instant;

//단건 상세 조회
public record ArchiveDetailRes(
        Long id,
        String title,
        String subtitle,
        String teamname,
        String content,
        String category,
        Instant createdAt
) {
    public static ArchiveDetailRes from(ArchivePost p) {
        return new ArchiveDetailRes(
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
