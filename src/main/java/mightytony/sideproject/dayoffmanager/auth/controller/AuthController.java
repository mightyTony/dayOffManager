package mightytony.sideproject.dayoffmanager.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateMasterRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberLoginRequestDto;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "유저(멤버)", description = "유저 관련 api 입니다")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

//    @Operation(summary = "로그인", description = "회원 로그인, 토큰 부여")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "CREATED"
//            //content = @Content(schema = @Schema(implementation = MemberCreateRequestDto.class)))
//            )
//    })
//    @PostMapping("/login")
//    public ResponseEntity<Void> signIn(@RequestBody MemberLoginRequestDto req, HttpServletResponse response) {
//        String userId = req.getUserId();
//        String password = req.getPassword();
//        JwtToken jwtToken = authService.signIn(userId, password);
//
//        response.addHeader("Authorization", jwtToken.getGrantType() + " " + jwtToken.getAccessToken());
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh",jwtToken.getRefreshToken())
//                .httpOnly(true)
//                .maxAge(REFRESH_TOKEN_EXPIRED_TIME)
//                .path("/")
//                .secure(true)
//                .sameSite("None")
//                .build();
//        response.setHeader("Set-Cookie", refreshTokenCookie.toString());
//        /* 기존 쿠키 방식
//        Cookie refreshCookie = new Cookie("refresh", jwtToken.getRefreshToken());
//        refreshCookie.setHttpOnly(true);
//        refreshCookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));
//        refreshCookie.setPath("/");
//        response.addCookie(refreshCookie);
//        */
//        return ResponseEntity.status(200).build();
//    }
@Operation(summary = "로그인", description = "회원 로그인, 토큰 부여")
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED"
                //content = @Content(schema = @Schema(implementation = MemberCreateRequestDto.class)))
        )
})
@PostMapping("/login")
public ResponseEntity<BasicResponse<Void>> signIn(@RequestBody MemberLoginRequestDto req, HttpServletResponse response) {
    String userId = req.getUserId();
    String password = req.getPassword();
    JwtToken jwtToken = authService.signIn(userId, password);

    response.addHeader("Authorization", jwtToken.getGrantType() + " " + jwtToken.getAccessToken());
    ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh",jwtToken.getRefreshToken())
            .httpOnly(true)
            .maxAge(REFRESH_TOKEN_EXPIRED_TIME)
            .path("/")
            .secure(true)
            .sameSite("None")
            .build();
    response.setHeader("Set-Cookie", refreshTokenCookie.toString());
        /* 기존 쿠키 방식
        Cookie refreshCookie = new Cookie("refresh", jwtToken.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
        */
    return ResponseUtil.ok(); //ResponseEntity.status(200).build();
}

    @Operation(summary = "테스트", description = "권한 인가 테스트")
    @PostMapping("/test")
    public ResponseEntity<BasicResponse<Void>> test() {

        return ResponseUtil.ok();
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/join")
    public ResponseEntity<Void> signUp(@RequestBody @Valid MemberCreateRequestDto req) {
        // 1. member 회원 가입 (이미 가입했는지 체크 로직)
        authService.signUp(req);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 CREATED
    }

    // 권한 테스트용 , 로그인 한 유저의 권한(auth)가 'ADMIN' 이거나 'TEAM_LEADER' 만 해당 메서드 접속 가능
    @GetMapping("/just")
    @PreAuthorize("hasRole('ADMIN')")
    public String just() {
        return "just Success";
    }

    @PostMapping("/logout")
    //@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','MASTER','TEAM_LEADER','USER')")
    public ResponseEntity<Void> logOut(HttpServletRequest request) {
        // 1. jwt 토큰 추출
        authService.logOut(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/master")
    public ResponseEntity<BasicResponse<Void>> masterRegister(@RequestBody @Valid MemberCreateMasterRequestDto req) {
        authService.registerMaster(req);

        return ResponseUtil.ok();
    }
}
