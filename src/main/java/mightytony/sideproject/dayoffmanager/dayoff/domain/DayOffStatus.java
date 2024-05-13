package mightytony.sideproject.dayoffmanager.dayoff.domain;

import lombok.Getter;

@Getter
public enum DayOffStatus {

    PENDING(1,"심사 대기 중"),
    APPROVED(2, "승인"),
    TL_APPROVED(3,"팀장 승인"),
    REJECTED(3, "반려");

    private final int code;
    private final String status;

    DayOffStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
