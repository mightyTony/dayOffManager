package mightytony.sideproject.dayoffmanager.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ExceptionStatus;
import mightytony.sideproject.dayoffmanager.member.domain.Member;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberAdminPromotionRequestDto;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.member.repository.MemberRepository;
import mightytony.sideproject.dayoffmanager.member.service.MemberService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    /**
     * authenticate() 메서드를 통해 Member 검증 진행
     */
    @Transactional(readOnly = true)
    @Override
    public JwtToken signIn(String userId, String password) {
        // ID 체크
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND_USER));
        // 비번 체크
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new CustomException(ExceptionStatus.PASSWORD_INVALID);
        }
        // 1. username + password 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청 된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행 될 때 CustomUserDetailService
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional(readOnly = false)
    @Override
    public void signUp(MemberCreateRequestDto req) {
        // 1. 이미 존재하는 지 체크
        if(memberRepository.existsMemberByUserIdAndPhoneNumber(req.getUserId(), req.getPhoneNumber())){
           throw new CustomException(ExceptionStatus.User_Already_Existed);
        }
        // 2. 가입 (가입 할 땐 회사 등록을 안 함)
        Member member = Member.builder()
                .userId(req.getUserId())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .profileImage(req.getProfileImage())
                .build();

        memberRepository.save(member);

        //log.info("member save = {}", memberRepository.save(member));
    }

    @Override
    public void sendRequestToMaster(MemberAdminPromotionRequestDto req) {
        // 1. Member 존재 확인
        Member member = memberRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionStatus.NOT_FOUND_USER));
        // 2. 요청 마스터에게 전달

        // 3. 마스터에게 요청 있는 지 체크
    }

}
