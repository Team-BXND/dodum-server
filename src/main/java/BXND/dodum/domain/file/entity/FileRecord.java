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
                @Index(name = "idx_files_key_name", columnList = "key_name", unique = true),
                @Index(name = "idx_files_entity", columnList = "entity_type, entity_id")
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

    // 업로더 (userId 등)
    @Column(name = "uploader_id", nullable = false, length = 100)
    private String uploaderId;

    // 정적 파일 접근용 URL
    @Column(name = "url", nullable = false, length = 512)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FileStatus status = FileStatus.PENDING;

    // 파일이 붙어있는 엔티티 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", length = 50)
    private FileEntityType entityType;

    // 붙어있는 엔티티 아이디
    @Column(name = "entity_id", length = 100)
    private String entityId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public FileRecord(String keyName, String originalName, String uploaderId, String url) {
        this.keyName = keyName;
        this.originalName = originalName;
        this.uploaderId = uploaderId;
        this.url = url;
        this.status = FileStatus.PENDING;
    }

    // 특정 엔티티에 파일을 붙임 (attach)
    public void attachTo(FileEntityType entityType, String entityId) {
        this.status = FileStatus.ATTACHED;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    // 스토리지 삭제 실패 등으로 soft delete만 수행
    public void softDelete() {
        this.status = FileStatus.SOFT_DELETED;
        this.deletedAt = OffsetDateTime.now();
    }
}
