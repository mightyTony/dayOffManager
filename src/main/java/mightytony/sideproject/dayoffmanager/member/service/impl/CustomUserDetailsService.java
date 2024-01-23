package mightytony.sideproject.dayoffmanager.member.service.impl;

import lombok.RequiredArgsConstructor;
import mightytony.sideproject.dayoffmanager.member.domain.member.Member;
import mightytony.sideproject.dayoffmanager.member.domain.member.MemberRole;
import mightytony.sideproject.dayoffmanager.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        // Null 체크
        List<MemberRole> roles = member.getRoles() != null ? member.getRoles() : new ArrayList<>();

        return User.builder()
//                .username(member.getUsername())
//                .password(passwordEncoder.encode(member.getPassword()))
//                .roles(member.getRoles().toArray(new String[0]))
//                .build();
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(roles.stream().map(MemberRole::name).toArray(String[]::new))
                .accountExpired(!member.isAccountNonExpired())
                .accountLocked(!member.isAccountNonLocked())
                .credentialsExpired(!member.isCredentialsNonExpired())
                .disabled(!member.isEnabled())
                .build();
    }
}
