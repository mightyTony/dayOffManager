package mightytony.sideproject.dayoffmanager.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.vacation.domain.Vacation;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 유저 테이블
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue @Comment("고유 번호")
    @Column(name = "member_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @Comment("회사")
    private Company company;

    @Column(name = "employee_number", unique = true)
    @Comment("사번")
    private String employeeNumber;

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

    @Column(name = "hire_date", updatable = false) //nullable = false
    @Comment("입사 날짜")
    private LocalDate hireDate;

    @Column(name = "resignation_date")
    @Comment("퇴사 날짜")
//    @Builder.Default
    private LocalDate resignationDate = null; // null = 재직 중

//    @Builder.Default
    @Comment("삭제 여부")
    @Column(name = "delete_yn", nullable = false)
    private String deleteYn = "N";

    @Comment("삭제 일시")
    @Column(name = "deleted_date")
    private LocalDate deleteDate;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Comment("직급")
    @Column(name = "roles", nullable = false)
    private List<MemberRole> roles = new ArrayList<>(Collections.singletonList(MemberRole.ADMIN));

    @Column(name = "vacation_count", nullable = false)
    @Comment("휴가 개수")
    private double vacationCount = 15.0;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vacation> vacations = new ArrayList<>();

    @Builder
    public Member(String password, String userId, String name, String email, String phoneNumber, String profileImage) {
        this.password = password;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    /**
     * Security 관련 메서드
     *
     */

    /* 유저의 권한 목록, 권한 반환 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        // fixme
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // fixme
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // fixme
        return true;
    }

    @Override
    public boolean isEnabled() {
        // fixme
        return true;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void delete() {

    }
}
