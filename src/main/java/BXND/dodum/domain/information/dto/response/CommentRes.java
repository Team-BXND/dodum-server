package BXND.dodum.domain.information.dto.response;

import BXND.dodum.domain.information.entity.InfoComment;

public record CommentRes(
        String content,
        String authorInfo,
        String createdAt
) {
    public static CommentRes from(InfoComment infoComment) {
        return new CommentRes(
                infoComment.getContent(),
                infoComment.getAuthor().getDisplayedName(),
                infoComment.getCreatedAt()
        );
    }
}
