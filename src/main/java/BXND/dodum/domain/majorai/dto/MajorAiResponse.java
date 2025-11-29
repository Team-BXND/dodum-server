package BXND.dodum.domain.majorai.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class MajorAiResponse {

    private String major;
    private String majorKey;
    private String selectedReason;
    private Map<String, Object> graph;
}
