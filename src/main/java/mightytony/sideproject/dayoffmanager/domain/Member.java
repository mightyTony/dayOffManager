package mightytony.sideproject.dayoffmanager.domain;

import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * 유저 테이블
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_member")
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_number", nullable = false, unique = true, updatable = false)
    @Comment("사번")
    private Integer employeeNumber;

    @Comment("비밀번호")
    private String password;

    @Comment("이름")
    private String name;

//    @Comment("직급")
//    private String position;

    @ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    @Column(name = "phone_number")
    @Comment("휴대폰 번호")
    private String phoneNumber;

    @Column(name = "profile_image")
    @Comment("프로필 사진")
    private String profileImage;

    @Column(name = "hire_date", updatable = false, nullable = false)
    @Comment("입사 날짜")
    private LocalDateTime hireDate;

    @Column(name = "resignation_date")
    @Comment("퇴사 날짜")
    private LocalDateTime resignationDate; // null = 재직 중

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
