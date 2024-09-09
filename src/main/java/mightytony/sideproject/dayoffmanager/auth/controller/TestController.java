package mightytony.sideproject.dayoffmanager.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/discord")
@Tag(name = "디스코드", description = "디스코드 테스트 api 입니다")
public class TestController {

    @GetMapping("/test")
    public void test(){
        log.warn("위험...");
        log.error("에러 발생 ! ");
        log.error("에러 발생 ! 2");
    }
}
