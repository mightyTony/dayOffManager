package mightytony.sideproject.dayoffmanager.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository memberRepository;
    //private final ApplicationContext context;

    // 전달 받은 아이디를 DB에서 조회해서 있는지 체크 한다.
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("토큰 재발급 신청한 유저 DB 에서 조회 : {} ", userId );

        return memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 하는 회원을 찾을 수 없습니다."));
    }


//    @Cacheable(value = "userCache", key = "#userId")
//    public UserDetails loadUserByUsernameWithCache(String userId) throws UsernameNotFoundException {
//        log.info("토큰 재발급 신청한 유저 DB 에서 조회 : {} ", userId );
//        return memberRepository.findByUserId(userId)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
//    }



    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        // Null 체크
        List<MemberRole> roles = member.getRoles() != null ? member.getRoles() : new ArrayList<>();

        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(roles.stream().map(MemberRole::name).toArray(String[]::new))
//                .accountExpired(!member.isAccountNonExpired())
//                .accountLocked(!member.isAccountNonLocked())
//                .credentialsExpired(!member.isCredentialsNonExpired())
//                .disabled(!member.isEnabled())
                .build();
    }

    public Authentication getUserAuthentication(String userId) {
        UserDetails userDetails = loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
