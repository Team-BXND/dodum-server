package BXND.dodum.domain.archive.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "archive_posts",
        indexes = {
                @Index(name = "idx_archive_category", columnList = "category"),
                @Index(name = "idx_archive_author", columnList = "author_id"),
                @Index(name = "idx_archive_created_at", columnList = "created_at DESC")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ArchivePost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false, length = 100)
    private String authorId; //로그인한 아이디

    @Column(nullable = false)
    private String title;

    @Column
    private String subtitle;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(nullable = false, length = 50)
    private String category;

    @Column
    private String teamname; // 개인 제출 null 허용

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(nullable = false)
    private boolean deleted;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void update(String title, String subtitle, String content,
                       String thumbnailUrl, String category, String teamname) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
        this.teamname = teamname;
    }
}
