package mightytony.sideproject.dayoffmanager.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberCreateRequestDto;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;

public interface AuthService {

    JwtToken signIn(String username, String password);

    void signUp(MemberCreateRequestDto req);

    void logOut(HttpServletRequest request);
}
