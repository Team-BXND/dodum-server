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
    List<CommentRes> comments
) {
    public  static ViewInfoRes of(Info info, String author, List<CommentRes> comments) {
        return new ViewInfoRes(
                info.getTitle(),
                info.getSubtitle(),
                info.getContent(),
                info.getImageUrls(),
                author,
                info.getCreatedAt(),
                comments

        );
    }
}
