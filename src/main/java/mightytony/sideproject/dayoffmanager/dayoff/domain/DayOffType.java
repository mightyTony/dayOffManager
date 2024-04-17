package mightytony.sideproject.dayoffmanager.dayoff.domain;

import lombok.Getter;

@Getter
public enum DayOffType {

    AM_HALF(2,"오전반차"),
    PM_HALF(3,"오후반차"),
    EARLY(4,"조퇴"),
    SICK(5,"병가"),
    REWARD(6, "포상휴가"),
    REFRESH(7,"리프레쉬휴가"),
    ETC(9,"기타 등등");

    private final int code;
    private final String description;

    DayOffType(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
