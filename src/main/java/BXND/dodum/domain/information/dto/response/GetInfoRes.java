package BXND.dodum.domain.information.dto.response;

import BXND.dodum.domain.information.entity.Info;

import java.util.List;

public record GetInfoRes(
   Long id,
   String title,
   String subTitle,
   int likes,
   int views,
   int comments,
   List<String> imageUrls,
   String createdAt
) {
    public static GetInfoRes from(Info info, String createdAt) {
        return new GetInfoRes(
                info.getId(),
                info.getTitle(),
                info.getSubtitle(),
                info.getLikesCount(),
                info.getViews(),
                info.getCommentCount(),
                info.getImageUrls(),
                info.getCreatedAt());
    }
}
