package mightytony.sideproject.dayoffmanager.service.impl;

import mightytony.sideproject.dayoffmanager.domain.member.Member;
import mightytony.sideproject.dayoffmanager.domain.MemberRole;
import mightytony.sideproject.dayoffmanager.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev") // dev 프로파일 환경에서 실행
class MemberServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 등록_BaseTimeEntity 적용테스트")
    public void BaseTimeEntity_post() {
        //given
        LocalDateTime now = LocalDateTime.now();
        memberRepository.save(Member.builder()
                        .employeeNumber(1234)
                        .password(passwordEncoder.encode("password1234"))
                .build());
        //when
        List<Member> memberList = memberRepository.findAll();
        //then
        Member member = memberList.get(0);

        System.out.println(">>>>>>>>>>>>>> createdDate = " + member.getCreatedDate() + " >>>> ModifiedDate = " + member.getModifiedDate());
        assertThat(member.getCreatedDate().isAfter(now));
        assertThat(member.getModifiedDate().isAfter(now));
    }
}