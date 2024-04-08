package mightytony.sideproject.dayoffmanager.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 유저 테이블
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE Member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue @Comment("고유 번호")
    private Long id;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(name = "user_id", nullable = false)
    @Comment("유저 아이디")
    private String userId;

    @Column(nullable = false)
    @Comment("이름")
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    @Comment("이메일")
    private String email;

    @Column(name = "phone_number")
    @Comment("휴대폰 번호")
    private String phoneNumber;

    @Column(name = "profile_image")
    @Comment("프로필 사진")
    //@Builder.Default
    private String profileImage = "default.jpg";

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE; // 기본 값을 False로 설정

    @Comment("삭제 일시")
    @Column(name = "deleted_date")
    private LocalDate deleteDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Comment("직급")
    @Column(name = "roles", nullable = false)
    private List<MemberRole> roles = new ArrayList<>(Collections.singletonList(MemberRole.USER));

    @Builder
    public Member(String password, String userId, String name, String email, String phoneNumber, String profileImage) {
        this.password = password;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
