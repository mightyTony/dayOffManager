package mightytony.sideproject.dayoffmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum ResponseCode {
    /**
     *  커스텀 응답 코드, 에러코드를 커스텀으로 만들어 에러의 원인을 쉽게 찾을 수 있다.
     */

    // 공통
    SUCCESS(200, "success"),

    //Company
    BUSINESSNUMBER_IS_ALREADY_EXIST(400,"이미 등록되어 있는 사업자등록번호 입니다."),
    NOT_FOUND_COMPANY(400, "해당 사업자등록번호로 된 업체가 존재하지 않습니다."),
    NOT_APPROVED(403,"해당 업체에 승인 되지 않은 유저 입니다."),

    // Token
    NOT_EXIST_TOKEN(400,"존재 하지 않는 토큰"),
    TokenSecurityExceptionOrMalformdJwtException(400, "유효하지 않은 JWT 서명 입니다."),
    TokenExpiredJwtException(400, "만료된 토큰 입니다."),
    TokenUnsupportedJwtException(400, "지원하지 않는 토큰 입니다"),
    TokenIllegalArgumentException(400, "올바른 형식이 아니거나 토큰이 비어있습니다."),
    RefreshTokenValidException(400, "리프래시 토큰이 존재 하지 않습니다."),
    TokenNeedReIssue(303, "액세스 토큰이 만료되었습니다. 재발급 해주세요."),
    InvalidAccessToken(400, "액세스 토큰이 유효하지 않습니다."),
    TokenMissingAuthorities(400, "권한 정보가 없는 토큰 입니다."),
    AlreadyLogout(403, "로그아웃 하여 토큰이 만료 되었습니다."),
    RedisUtilNullException(500, "레디스 서버 문제 입니다."),


    // Member (유저)
    NOT_FOUND_USER(401, "해당 유저를 찾을 수 없습니다."),
    User_Already_Existed(400, "해당 아이디는 이미 등록되어있습니다."),
    EMAIL_EXISTED(400,"이미 등록된 이메일 입니다."),
    PHONE_NUMBER_EXISTED(400,"이미 등록 된 전화번호 입니다."),
    PASSWORD_INVALID(400, "비밀번호 틀렸어요");

    private int statusCode;
    private String message;
}
