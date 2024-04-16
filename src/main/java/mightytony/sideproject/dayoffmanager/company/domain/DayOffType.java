package mightytony.sideproject.dayoffmanager.company.domain;

import lombok.Getter;

@Getter
public enum DayOffType {
//    ANNUAL_VACATION(01,"연차"),
//    SICK_VACATION(02"병가"),
//    HALF_DAY_AM("반차 (오전)"),
//    HALF_DAY_PM("반차 (오후)");
//
    CARRY_OVER(1,"이월연차"),
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
