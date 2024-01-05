package mightytony.sideproject.dayoffmanager.service;

import mightytony.sideproject.dayoffmanager.config.jwt.JwtToken;

public interface MemberService {

    public JwtToken sign_in(String username, String password);
}
