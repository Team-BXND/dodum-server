package BXND.dodum.domain.information.dto.response;

import java.util.List;

public record GetInfoRes(
   Long id,
   String title,
   String author,
   int likes,
   int views,
   int comments,
   List<String> imageUrls,
   String createdAt
) {
}
