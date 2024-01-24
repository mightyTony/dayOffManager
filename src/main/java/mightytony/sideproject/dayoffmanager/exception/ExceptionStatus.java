package mightytony.sideproject.dayoffmanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {
    /**
     *  커스텀 에러 코드, 에러코드를 커스텀으로 만들어 에러의 원인을 쉽게 찾을 수 있다.
     */

    //Company
    BUSINESSNUMBER_IS_ALREADY_EXIST(400,"이미 등록되어 있는 사업자등록번호 입니다."),
    NOT_FOUND_COMPANY(404, "해당 사업자등록번호로 된 업체가 존재하지 않습니다."),

    // Token
    TokenSecurityExceptionOrMalformdJwtException(400, "유효하지 않은 JWT 서명 입니다."),
    TokenExpiredJwtException(400, "만료된 토큰 입니다."),
    TokenUnsupportedJwtException(400, "지원하지 않는 토큰 입니다"),
    TokenIllegalArgumentException(400, "올바른 형식이 아니거나 토큰이 비어있습니다."),
    RefreshTokenValidException(400, "리프래시 토큰이 존재 하지 않습니다."),
    TokenNeedReIssue(303, "액세스 토큰이 만료되었습니다. 재발급 해주세요."),
    InvalidAccessToken(400, "액세스 토큰이 유효하지 않습니다."),
    TokenMissingAuthorities(400, "권한 정보가 없는 토큰 입니다."),

    // Member
    Not_Found_User(404, "해당 유저를 찾을 수 없습니다."),
    User_Already_Existed(400, "해당 아이디는 이미 등록되어있습니다.");

    private final int statusCode;
    //private final HttpStatus;
    private final String message;
}
