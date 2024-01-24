package mightytony.sideproject.dayoffmanager.member.repository;

import mightytony.sideproject.dayoffmanager.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    //회원 정보 생성
    @Test
    void saveMember() {
        Member member = Member.builder()
                .name("tony")
                .password("1234")
                .phoneNumber("01053612197")
                .employeeNumber(1234123)
                .email("sdf@test.com")
                .build();

        Member tony = memberRepository.save(member);

        assertEquals(tony.getName(), "tony");
    }
}