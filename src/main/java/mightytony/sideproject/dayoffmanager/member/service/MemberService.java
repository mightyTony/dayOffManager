package mightytony.sideproject.dayoffmanager.member.service;

import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;

public interface MemberService {

    public JwtToken signIn(String username, String password);
}
