package BXND.dodum.domain.information.dto.response;

public record CommentRes(
        String content,
        String authorInfo,
        String createdAt
)
{ }
