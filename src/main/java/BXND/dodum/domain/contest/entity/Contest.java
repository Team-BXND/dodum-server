package BXND.dodum.domain.contest.entity;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.contest.dto.request.CreateContestReq;
import BXND.dodum.domain.information.entity.InfoLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목을 필수입력입니다.")
    private String title;
    private String subTitle;
    @NotBlank(message = "장소는 필수입력입니다.")
    private String place;
    @NotBlank(message = "전화번호는 필수입력입니다.")
    private String phone;
    @NotBlank(message = "이메일은 필수입력입니다.")
    @Email
    private String email;
    @NotBlank(message = "시간은 필수 입력입니다.")
    private String time;
    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users author;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ContestAlarm> alarm = new ArrayList<>();

    public void updateContest(CreateContestReq request) {
        this.title = request.title();
        this.subTitle = request.subTitle();
        this.place = request.place();
        this.phone = request.phone();
        this.email = request.email();
        this.time = request.time();
        this.content = request.content();
    }
}
