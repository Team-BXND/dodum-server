package BXND.dodum.domain.file.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "files",
        indexes = {
                @Index(name = "idx_files_status", columnList = "status"),
                @Index(name = "idx_files_created_at", columnList = "created_at"),
                @Index(name = "idx_files_uploader", columnList = "uploader_id"),
                @Index(name = "idx_files_key_name", columnList = "key_name", unique = true)
        }
)
@Getter
@NoArgsConstructor
public class FileRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="key_name", nullable=false, length = 255) // 스토리지 안에서 파일이름
    private String keyName;

    @Column(name="original_name", nullable=false, length = 255) //사용자가 업로드한 파일 이름
    private String originalName;

    @Column(name="uploader_id", nullable=false, length = 100)
    private String uploaderId;

    @Column(name="url", nullable=false, length = 512)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length = 20)
    private FileStatus status = FileStatus.PENDING;

    @Column(name="entity_type", length = 50) //파일 어떤형식 제출인지
    private String entityType;

    @Column(name="entity_id", length = 100) //파일 어디에 붙어있는지
    private String entityId;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private OffsetDateTime createdAt;

    @Column(name="deleted_at")
    private OffsetDateTime deletedAt;

    public FileRecord(String keyName, String originalName, String uploaderId, String url) {
        this.keyName = keyName;
        this.originalName = originalName;
        this.uploaderId = uploaderId;
        this.url = url;
        this.status = FileStatus.PENDING;
    }

    public void attachTo(String entityType, String entityId) {
        this.status = FileStatus.ATTACHED;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public void softDelete() {
        this.status = FileStatus.SOFT_DELETED;
        this.deletedAt = OffsetDateTime.now();
    }
}
