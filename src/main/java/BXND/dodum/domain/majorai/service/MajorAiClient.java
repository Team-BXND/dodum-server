package BXND.dodum.domain.majorai.service;

import BXND.dodum.domain.majorai.dto.MajorAiResponse;
import BXND.dodum.domain.majorai.dto.RecommendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MajorAiClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://127.0.0.1:8000")  // fastapi 서버 url
            .build();

    public MajorAiResponse requestAI(RecommendRequest request) {
        return webClient.post()
                .uri("/major-recommend")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MajorAiResponse.class)
                .block();
    }
}
