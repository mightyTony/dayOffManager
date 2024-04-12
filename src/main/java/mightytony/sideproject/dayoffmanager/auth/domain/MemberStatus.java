package mightytony.sideproject.dayoffmanager.auth.domain;

import lombok.Getter;

@Getter
public enum MemberStatus {

    PENDING(1, "심사 대기 중"),
    APPROVED(2, "승인"),
    REJECTED(3,"반려");

    private final int code;
    private final String status;

    MemberStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
