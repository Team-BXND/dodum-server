package BXND.dodum.domain.majorai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorAiResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String majorKey;
    private String majorName;

    @Column(columnDefinition = "TEXT")
    private String selectedReason;

    @Column(columnDefinition = "TEXT")
    private String graphJson;

    private Instant createdAt;
}
