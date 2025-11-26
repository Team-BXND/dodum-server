package BXND.dodum.domain.majorai.controller;

import BXND.dodum.domain.majorai.dto.MajorAiResponse;
import BXND.dodum.domain.majorai.dto.RecommendRequest;
import BXND.dodum.domain.majorai.service.MajorAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/major-ai")
public class MajorAiController {

    private final MajorAiService majorAiService;

    @PostMapping("/recommend")
    public MajorAiResponse recommend(@RequestBody @Valid RecommendRequest request) throws Exception {
        return majorAiService.recommend(request);
    }
}
