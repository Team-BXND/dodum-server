package BXND.dodum.domain.information.dto.response;

import BXND.dodum.domain.information.entity.Info;

import java.util.List;

public record ViewInfoRes(
    String title,
    String subTitle,
    String content,
    List<String> imageUrls,
    String author,
    String createdAt,
    List<CommentRes> comments,
    int likes,
    int views,
    int commentCount
) {
    public  static ViewInfoRes of(Info info, String author, List<CommentRes> comments) {
        return new ViewInfoRes(
                info.getTitle(),
                info.getSubTitle(),
                info.getContent(),
                info.getImageUrls(),
                author,
                info.getCreatedAt(),
                comments,
                info.getLikesCount(),
                info.getViews(),
                info.getCommentCount()
        );
    }
}
