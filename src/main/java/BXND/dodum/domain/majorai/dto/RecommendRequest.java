package BXND.dodum.domain.majorai.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class RecommendRequest {
    private Map<String, String> object;
    private Map<String, String> subject;
}
