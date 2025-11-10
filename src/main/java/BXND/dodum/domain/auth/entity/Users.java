package BXND.dodum.domain.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @NotBlank
    @Column(nullable = false)
    @Email
    private String email;
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
    private String club;
    @Column(columnDefinition = "TEXT")
    private String history;

    @Builder.Default
    private Role role = Role.STUDENT;
}
