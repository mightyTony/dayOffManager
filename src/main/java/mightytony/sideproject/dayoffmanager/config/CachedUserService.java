package mightytony.sideproject.dayoffmanager.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CachedUserService {

    private final AuthRepository memberRepository;

    @Cacheable(value = "userDetailCache", key = "#userId")
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        List<MemberRole> roles = member.getRoles() != null ? member.getRoles() : new ArrayList<>();

        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(roles.stream().map(MemberRole::name).toArray(String[]::new))
                .build();
    }
}
