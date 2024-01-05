package mightytony.sideproject.dayoffmanager.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    EMPLOYEE("사원"),
    TEAM_LEADER("팀장"),
    ADMIN("관리자"),
    MASTER("마스터");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
