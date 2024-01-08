package mightytony.sideproject.dayoffmanager.service;

import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;

public interface MemberService {

    public JwtToken signIn(String username, String password);
}
