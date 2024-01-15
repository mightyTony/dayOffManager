package mightytony.sideproject.dayoffmanager.vacation.domain;

public enum VacationType {
    ANNUAL_LEAVE("연차"),
    SICK_LEAVE("병가"),
    HALF_DAY_AM("반차 (오전)"),
    HALF_DAY_PM("반차 (오후)");

    private final String description;

    VacationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
