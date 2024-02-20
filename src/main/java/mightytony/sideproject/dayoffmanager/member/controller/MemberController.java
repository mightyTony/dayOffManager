package mightytony.sideproject.dayoffmanager.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberLoginRequestDto;
import mightytony.sideproject.dayoffmanager.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "유저(멤버)", description = "유저 관련 api 입니다")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "회원 로그인, 토큰 부여")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"
            //content = @Content(schema = @Schema(implementation = MemberCreateRequestDto.class)))
            )
    })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody MemberLoginRequestDto req) {
        String userId = req.getUserId();
        String password = req.getPassword();
        JwtToken jwtToken = memberService.signIn(userId, password);

//        log.info("request username = {}, password = {} ", userId,password );
//        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return ResponseEntity.status(200).body(jwtToken);
    }

    @Operation(summary = "테스트", description = "권한 인가 테스트")
    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequestDto req) {
        // 1. member 회원 가입 (이미 가입했는지 체크 로직)
        memberService.signUp(req);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 CREATED
    }

    // 권한 테스트용 , 로그인 한 유저의 권한(auth)가 'ADMIN' 이거나 'TEAM_LEADER' 만 해당 메서드 접속 가능
    @GetMapping("/just")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEAM_LEADER')")
    public String just() {
        return "just Success";
    }


}
