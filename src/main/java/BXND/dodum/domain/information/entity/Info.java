package BXND.dodum.domain.information.entity;

import BXND.dodum.domain.auth.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "글의 내용은 필수 입력입니다.")
    private String content;

    @Column(nullable = false)
    private String createdAt;

    @ElementCollection
    @Column(name = "image_url")
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "info", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InfoLike> infoLikes = new ArrayList<>();

    @Builder.Default
    private int views = 0;
    @Builder.Default
    @Setter
    private boolean isApproved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Users author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void incrementViews() {
        this.views++;
    }

    public int getLikesCount() {
        return infoLikes.size();
    }

}
