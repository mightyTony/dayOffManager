package mightytony.sideproject.dayoffmanager.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateMasterRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberUpdateResponseDto;

import java.util.Map;

public interface AuthService {

    //JwtToken signIn(String username, String password);
    Map<String, Object> signIn(String username, String password);

    void signUp(MemberCreateRequestDto req);

    void logOut(HttpServletRequest request, HttpServletResponse response);

    void registerMaster(MemberCreateMasterRequestDto req);

    String refreshAccessToken(HttpServletRequest req);

    MemberUpdateResponseDto updateUserInfo(HttpServletRequest req, String userId, MemberUpdateRequestDto updateRequestDto);
}
