package BXND.dodum.domain.majorai.service;

import BXND.dodum.domain.majorai.dto.MajorAiResponse;
import BXND.dodum.domain.majorai.dto.RecommendRequest;
import BXND.dodum.domain.majorai.entity.MajorAiResult;
import BXND.dodum.domain.majorai.repository.MajorAiRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MajorAiService {

    private final MajorAiClient majorAiClient;
    private final MajorAiRepository majorAiRepository;
    private final ObjectMapper objectMapper;

    private String currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            throw new RuntimeException("UNAUTHENTICATED");
        }
        return auth.getName(); // username
    }

    @Transactional
    public MajorAiResponse recommend(RecommendRequest req) throws Exception {

        String username = currentUserId();

        MajorAiResponse aiRes = majorAiClient.requestAI(req);

        String graphJson = objectMapper.writeValueAsString(aiRes.getGraph());

        MajorAiResult result = MajorAiResult.builder()
                .userId(username)
                .majorKey(aiRes.getMajorKey())
                .majorName(aiRes.getMajor())
                .selectedReason(aiRes.getSelectedReason())
                .graphJson(graphJson)
                .createdAt(Instant.now())
                .build();

        majorAiRepository.save(result);

        return aiRes;
    }
}
