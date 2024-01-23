package mightytony.sideproject.dayoffmanager.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.member.domain.member.dto.MemberLoginRequestDto;
import mightytony.sideproject.dayoffmanager.member.domain.member.dto.SignInDto;
import mightytony.sideproject.dayoffmanager.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


//    @Operation(summary = "로그인", description = "회원 로그인, 토큰 부여", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "CREATED",
//            content = @Content(schema = @Schema(implementation = )))
//    })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody MemberLoginRequestDto req) {
        String userId = req.getUserId();
        String password = req.getPassword();
        JwtToken jwtToken = memberService.signIn(userId, password);

        log.info("request username = {}, password = {} ", userId,password );
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return ResponseEntity.status(200).body(jwtToken);
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
