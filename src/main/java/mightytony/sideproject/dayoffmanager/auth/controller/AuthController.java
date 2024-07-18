package mightytony.sideproject.dayoffmanager.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateMasterRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberLoginRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;

import java.util.Map;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "유저(멤버)", description = "유저 관련 api 입니다")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "회원 로그인, 토큰 부여")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"
                    //content = @Content(schema = @Schema(implementation = MemberCreateRequestDto.class)))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<BasicResponse<MemberLoginResponseDto>> signIn(@RequestBody MemberLoginRequestDto req, HttpServletResponse response) {
        String userId = req.getUserId();
        String password = req.getPassword();

        //JwtToken jwtToken = authService.signIn(userId, password);
        Map<String, Object> loginResponse = authService.signIn(userId, password);
        JwtToken jwtToken = (JwtToken) loginResponse.get("token");
        MemberLoginResponseDto member_info = (MemberLoginResponseDto) loginResponse.get("member_info");


        response.addHeader("Authorization", jwtToken.getGrantType() + " " + jwtToken.getAccessToken());
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh",jwtToken.getRefreshToken())
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_EXPIRED_TIME)
                .path("/")
                //FIXME 나중에 HTTPS 할 시 secure(true)로 변경
                .secure(true)
                //FIXME
                .sameSite("None")
                //.sameSite("Lax")
                .build();
        response.setHeader("Set-Cookie", refreshTokenCookie.toString());
            /* 기존 쿠키 방식
            Cookie refreshCookie = new Cookie("refresh", jwtToken.getRefreshToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));
            refreshCookie.setPath("/");
            response.addCookie(refreshCookie);
            */

        // 로그인 한 유저 정보 주기
        return ResponseUtil.ok(member_info); //ResponseEntity.status(200).build();
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/join")
    public ResponseEntity<BasicResponse<Void>> signUp(@RequestBody @Valid MemberCreateRequestDto req) {
        // 1. member 회원 가입 (이미 가입했는지 체크 로직)
        authService.signUp(req);

        return ResponseUtil.ok();
    }

    @PostMapping("/logout")
    //@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','MASTER','TEAM_LEADER','USER')")
    public ResponseEntity<BasicResponse<Void>> logOut(HttpServletRequest request, HttpServletResponse response) {
        // 1. jwt 토큰 추출
        authService.logOut(request, response);

        return ResponseUtil.ok();
    }

    @PostMapping("/master")
    public ResponseEntity<BasicResponse<Void>> masterRegister(@RequestBody @Valid MemberCreateMasterRequestDto req) {
        authService.registerMaster(req);

        return ResponseUtil.ok();
    }


    @Operation(summary = "토큰 갱신", description = "액세스 토큰 갱신")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<BasicResponse<String>> refreshToken(HttpServletRequest req) {
        String newAccessToken = authService.refreshAccessToken(req);
        return ResponseUtil.ok(newAccessToken);
    }
}
