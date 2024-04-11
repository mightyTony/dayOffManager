package mightytony.sideproject.dayoffmanager.admin.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.admin.domain.dto.request.AdminInviteNewMemberRequestDto;
import mightytony.sideproject.dayoffmanager.admin.service.AdminService;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AuthServiceImpl authService;
    private final JwtTokenProvider jwtTokenProvider;
    public ResponseEntity<BasicResponse<Void>> inviteNewEmployee(AdminInviteNewMemberRequestDto requestDto, HttpServletRequest request) {

        // 1. Admin 체크
        String accessTokenFromRequest = authService.getAccessTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessTokenFromRequest);

        // 2. 해당 어드민이 소속된 회사 값 가져오기

        // 3. 새로 온 사람 회사에 소속 시키기(Member -> Employee)
        return ResponseUtil.ok();
    }
}
