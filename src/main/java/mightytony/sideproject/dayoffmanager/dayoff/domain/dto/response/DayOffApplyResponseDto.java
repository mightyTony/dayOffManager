package mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response;

import lombok.Data;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;

import java.time.LocalDate;

@Data
public class DayOffApplyResponseDto {
    private String userId;
    private Long companyId;
    private String companyName;
    private DayOffType type;
    private double duration;
    private DayOffStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}