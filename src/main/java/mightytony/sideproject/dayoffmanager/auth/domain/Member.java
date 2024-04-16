package mightytony.sideproject.dayoffmanager.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
@SQLDelete(sql = "UPDATE Member SET deleted = true WHERE member_id = ?")
@Where(clause = "deleted = false")
@Builder
public class Member extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    @Comment("이름")
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image")
    @Builder.Default
    private String profileImage = "default.jpg";

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE; // 기본 값을 False로 설정

    @Column(name = "deleted_date")
    private LocalDate deleteDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    @Builder.Default
    private List<MemberRole> roles = new ArrayList<>(Collections.singletonList(MemberRole.EMPLOYEE));

    @Column(name = "employee_number", unique = true)
    private String employeeNumber;

    @Column(name = "resignation_date")
    @Builder.Default
    private LocalDate resignationDate = null;

    @Column(name = "dayoff_count", nullable = false)
    @Builder.Default
    private double dayOffCount = 0.0;

    // 회사 등록 승인
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Comment("회사 등록 승인")
    @Builder.Default
    private MemberStatus status = MemberStatus.PENDING;

    // 유저 -> 어드민 업데이트
    public void updateToAdmin() {
        this.status = MemberStatus.APPROVED;
        this.roles = Collections.singletonList(MemberRole.ADMIN);
    }
}
