package mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response;

import lombok.Data;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;

@Data
public class DayOffApplyResponseDto {
    private String userId;
    private Long companyId;
    private String companyName;
    private DayOffType type;
    private double duration;
    private DayOffStatus status;
}

/**
 *     private Long id;
 *     private Member member;
 *     private DayOffType type;
 *     private Integer duration;
 *     private DayOffStatus status;
 */