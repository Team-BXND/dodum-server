package BXND.dodum.domain.auth.entity;

import BXND.dodum.domain.profile.dto.request.UpdateProfileReq;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.userdetails.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "id는 필수입니다.")
    private String username;
    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    @NotBlank(message = "이메일은 필수입니다.")
    @Column(nullable = false)
    @Email
    private String email;
    @NotBlank(message = "전화번호는 필수입니다.")
    @Column(length = 45, nullable = false)
    private String phone;
    @Column(length = 45)
    private String major;
    private int grade;
    private int class_no;
    private int student_no;
    @Builder.Default
    @Column(columnDefinition = "TEXT")
    private String profile = "http://localhost:8080/dodum/images/profile.png";
    @Column(length = 45)
    private Club club;
    @Column(columnDefinition = "TEXT")
    private String history;

    private Role role;

    public void updateProfile(UpdateProfileReq request) {
        this.club = request.club();
        this.email = request.email();
        this.phone = request.phone();
        this.class_no = request.class_no();
        this.student_no = request.student_no();
        this.grade = request.grade();
    }

    public String getDisplayedName() {
        int studentNumber = (this.grade * 1000) + (this.class_no * 100) + this.student_no;
        return studentNumber + " " + this.username;
    }
}
