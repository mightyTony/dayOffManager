package mightytony.sideproject.dayoffmanager.dayoff.domain;

import lombok.Getter;

@Getter
public enum DayOffType {
    NORMAL(1, "휴가"),
    AM_HALF(2,"오전 반차"),
    PM_HALF(3,"오후 반차"),
    EARLY(4,"조퇴"),
    SICK(5,"병가"),
    REWARD(6, "포상 휴가"),
    AM_QUARTER(7, "오전 반반차"),
    PM_QUARTER(8, "오후 반반차"),
    ETC(9,"기타 등등");

    private final int code;
    private final String description;

    DayOffType(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
