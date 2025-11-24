package BXND.dodum.domain.information.dto.response;

import BXND.dodum.domain.information.entity.Info;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public record GetInfoRes(
   Long id,
   String title,
   String author,
   int likes,
   int views,
   List<String> imageUrls,
   String createdAt
) {
    public static GetInfoRes from(Info info) {
        return new GetInfoRes(
                info.getId(),
                info.getTitle(),
                info.getAuthor().getDisplayedName(),
                info.getLikesCount(),
                info.getViews(),
                info.getImageUrls(),
                info.getCreatedAt()
        );
    }

    public static List<GetInfoRes> fromPage(Page<Info> infoPage) {
        return infoPage.getContent().stream()
                .map(GetInfoRes::from)
                .collect(Collectors.toList());
    }
}
