package BXND.dodum.domain.information.dto.request;

import java.util.List;

public record CreateInfoReq(
        String title,
        String subTitle,
        String content,
        List<String> imageUrls
) {
}
