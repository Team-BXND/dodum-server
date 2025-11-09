package BXND.dodum.domain.information.entity;

import BXND.dodum.domain.auth.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;
    private String content;

    @Column(nullable = false)
    private String createdAt;

    @ElementCollection
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @Builder.Default
    private int likes = 0;

    @Builder.Default
    private int views = 0;
    @Builder.Default
    @Setter
    private boolean isApproved = false;

    @OneToMany(mappedBy = "info", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InfoComment> infoComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Users authors;

    public void update(String title, String subtitle, String content) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
    }

    public void incrementViews() {
        this.views++;
    }

    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    public int getCommentCount() {
        return infoComments.size();
    }
}
