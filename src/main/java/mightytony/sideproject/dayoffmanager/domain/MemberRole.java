package mightytony.sideproject.dayoffmanager.domain;

public enum MemberRole {
    EMPLOYEE("사원"),
    TEAM_LEADER("팀장"),
    ADMIN("관리자");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
