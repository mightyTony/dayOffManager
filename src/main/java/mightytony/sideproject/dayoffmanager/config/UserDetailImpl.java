package mightytony.sideproject.dayoffmanager.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class UserDetailImpl implements UserDetails {
    /**
     * Security 관련 메서드
     *
     */

    private Member member;
    private String userId;

    public UserDetailImpl(Member member, String userId) {
        this.member = member;
        this.userId = userId;
    }

    /* 유저의 권한 목록, 권한 반환 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
