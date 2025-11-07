package BXND.dodum.domain.file.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "files")
@Getter
@NoArgsConstructor
public class FileRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="key_name", nullable=false)
    private String keyName;

    @Column(name="original_name", nullable=false)
    private String originalName;

    @Column(name="uploader_id", nullable=false)
    private String uploaderId;

    @Column(name="url", nullable=false)
    private String url;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private OffsetDateTime createdAt;

    public FileRecord(String keyName, String originalName, String uploaderId, String url) {
        this.keyName = keyName;
        this.originalName = originalName;
        this.uploaderId = uploaderId;
        this.url = url;
    }
}
