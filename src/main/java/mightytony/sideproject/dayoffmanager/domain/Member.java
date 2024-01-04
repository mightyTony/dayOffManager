package mightytony.sideproject.dayoffmanager.domain;

import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "phone_number")
    @Comment("휴대폰 번호")
    private String phoneNumber;

    @Column(name = "profile_image")
    @Comment("프로필 사진")
    private String profileImage;

    @Column(name = "hire_date", updatable = false) //nullable = false
    @Comment("입사 날짜")
    private LocalDateTime hireDate;

    @Column(name = "resignation_date")
    @Comment("퇴사 날짜")
    private LocalDateTime resignationDate; // null = 재직 중

    @ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    @Builder.Default
    @Comment("삭제 여부")
    @Column(name = "delete_yn", nullable = false)
    private String deleteYn = "N";

    @Comment("삭제 일시")
    @Column(name = "deleted_date")
    private LocalDateTime deleteDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(roles -> new SimpleGrantedAuthority("ROLE_" + roles.name()))
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

    public void  delete() {

    }
}
