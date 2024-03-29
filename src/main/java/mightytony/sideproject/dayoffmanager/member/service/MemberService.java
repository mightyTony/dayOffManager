package mightytony.sideproject.dayoffmanager.member.service;

import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberAdminPromotionRequestDto;
import mightytony.sideproject.dayoffmanager.member.domain.dto.request.MemberCreateRequestDto;

public interface MemberService {

    JwtToken signIn(String username, String password);

    void signUp(MemberCreateRequestDto req);

    void sendRequestToMaster(MemberAdminPromotionRequestDto req);
}
