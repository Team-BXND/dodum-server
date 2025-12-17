package BXND.dodum.domain.information.dto.response;

import BXND.dodum.domain.information.entity.Info;

import java.util.List;
import java.util.stream.Collectors;

public record ViewInfoRes(
    String title,
    String content,
    List<String> imageUrls,
    String author,
    String createdAt,
    int likes,
    int views
) {
    public  static ViewInfoRes of(Info info, String author) {

        return new ViewInfoRes(
                info.getTitle(),
                info.getContent(),
                info.getImageUrls(),
                author,
                info.getCreatedAt(),
                info.getLikesCount(),
                info.getViews()
        );
    }
}
