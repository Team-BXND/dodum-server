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
   String createdAt,
   Long totalCount
) {
    public static GetInfoRes from(Info info) {
        return new GetInfoRes(
                info.getId(),
                info.getTitle(),
                info.getAuthor().getDisplayedName(),
                info.getLikesCount(),
                info.getViews(),
                info.getImageUrls(),
                info.getCreatedAt(),
                null
        );
    }

    public static GetInfoRes from(Info info, Long totalCount) {
        return new GetInfoRes(
                info.getId(),
                info.getTitle(),
                info.getAuthor().getDisplayedName(),
                info.getLikesCount(),
                info.getViews(),
                info.getImageUrls(),
                info.getCreatedAt(),
                totalCount
        );
    }

    public static List<GetInfoRes> fromPage(Page<Info> infoPage) {
        long totalCount = infoPage.getTotalElements();
        return infoPage.getContent().stream()
                .map(info -> GetInfoRes.from(info, totalCount))
                .collect(Collectors.toList());
    }
}
