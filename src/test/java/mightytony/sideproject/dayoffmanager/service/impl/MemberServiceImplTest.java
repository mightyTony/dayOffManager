package mightytony.sideproject.dayoffmanager.service.impl;

import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
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
    AuthRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @AfterEach
//    public void cleanUp() {
//        memberRepository.deleteAll();
//    }
}