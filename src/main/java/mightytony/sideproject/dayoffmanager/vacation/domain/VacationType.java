package mightytony.sideproject.dayoffmanager.vacation.domain;

import lombok.Getter;

@Getter
public enum VacationType {
//    ANNUAL_VACATION(01,"연차"),
//    SICK_VACATION(02"병가"),
//    HALF_DAY_AM("반차 (오전)"),
//    HALF_DAY_PM("반차 (오후)");
//
    CARRY_OVER_VACATION(1,"이월연차"),
    AM_HALF_VACATION(2,"오전반차"),
    PM_HALF_DAY(3,"오후반차"),
    EARLY_VACATION(4,"조퇴"),
    SICK_VACATION(5,"병가"),
    REWARD_VACATION(6, "포상휴가"),
    REFRESH_VACATION(7,"리프레쉬휴가"),
    CONDOLENCE_VACATION(8,"경조사휴가"),
    ETC(9,"기타 등등");

    private final int code;
    private final String description;

    VacationType(int code,String description) {
        this.code = code;
        this.description = description;
    }

}
