package mightytony.sideproject.dayoffmanager.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.domain.member.dto.SignInDto;
import mightytony.sideproject.dayoffmanager.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * @apiNote 회원가입
     * @param signInDto
     * @return  JWT 토큰
     */
//    @Operation(summary = "회원 가입 요청", description = "회원 가입, 토큰 부여", tags = {"Member Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "CREATED",
//            content = @Content(schema = @Schema(implementation = )))
//    })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getName();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);

        log.info("request username = {}, password = {} ", username,password );
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return ResponseEntity.status(201).body(jwtToken);
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
