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
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateMasterRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberLoginRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberUpdateResponseDto;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import mightytony.sideproject.dayoffmanager.auth.service.AuthService;
import mightytony.sideproject.dayoffmanager.auth.service.S3Service;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.config.redis.RedisUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "유저(멤버)", description = "유저 관련 api 입니다")
public class AuthController {

    private final AuthService authService;
    //private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final S3Service s3service;
    private final RedisUtil redisUtil;

    @Value("${cloud.aws.cloudfront.url")
    private String cloudfrontUrl;

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

        log.info("MemberInfo : {} ", member_info);

        response.addHeader("authorization", jwtToken.getGrantType() + " " + jwtToken.getAccessToken());
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

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    //@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','MASTER','TEAM_LEADER','USER')")
    public ResponseEntity<BasicResponse<Void>> logOut(HttpServletRequest request, HttpServletResponse response) {
        // 1. jwt 토큰 추출
        authService.logOut(request, response);

        return ResponseUtil.ok();
    }

    @Operation(summary = "마스터 계정 생성")
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

    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS")
    })
    @PatchMapping("/info/{userId}")
    public ResponseEntity<BasicResponse<MemberUpdateResponseDto>> updateMemberInformation(HttpServletRequest req,
                                                                                          @PathVariable String userId,
                                                                                          @RequestBody MemberUpdateRequestDto updateRequestDto) {
        MemberUpdateResponseDto dto = authService.updateUserInfo(req, userId, updateRequestDto);

        return ResponseUtil.ok(dto);
    }

    /**
     * 프사 바꾸는 폼 이후 유저 정보 업데이트 창에서 ok 누를시 저장되게 / ok 안 누르면 저장안됨.
     */
    @Operation(summary = "프로필 이미지 업로드", description = "프로필 이미지 업로드")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"
            )
    })
    @PostMapping("/info/{userId}/profileImage")
    //@Transactional
    public ResponseEntity<BasicResponse<String>> uploadProfileImage(@RequestParam(value = "file")MultipartFile file,
                                                                  @PathVariable String userId) throws IOException {
        // 1. 유저 체크
        Member member = authRepository.findByUserId(userId).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_USER));

        // 2. 유저 프로필 업데이트
        String s3ProfileImageUrl = s3service.uploadFile(file);
        log.info("LOG :: {} upload profileImage ", userId);
//        member.updateProfileImage(profileImageUrl);
//        authRepository.save(member);
//
//        // 3. 캐시 삭제/ 신규 정보 캐시 저장
//        redisUtil.deleteUserFromCache(userId);

        // 3. profile Url 주기
        return ResponseUtil.ok(s3ProfileImageUrl);
    }

    @DeleteMapping("/info/{userId}/profileImage")
    @Transactional
    public ResponseEntity<BasicResponse<Void>> deleteProfileImage(@RequestParam String imageUrl,
                                                                  @PathVariable String userId,
                                                                  HttpServletRequest request) {
        String requesterId = authService.isThatYou(request);

        if(!userId.equals(requesterId)){
            throw new CustomException(ResponseCode.PERMISSION_DENIED);
        }

        Member member = authRepository.findByUserId(userId).orElseThrow(()-> new CustomException(ResponseCode.NOT_FOUND_USER));
        //FIXME 기본 이미지 s3에 올려둬서 써야할 듯
        member.updateProfileImage("default.jpg");

        s3service.deleteImageFromS3(imageUrl);
        authRepository.save(member);

        return ResponseUtil.ok();
    }
}
