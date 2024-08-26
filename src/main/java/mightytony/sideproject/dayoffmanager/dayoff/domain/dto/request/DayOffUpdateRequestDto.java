package mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Data
public class DayOffUpdateRequestDto {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("휴가 유형")
    private DayOffType type;

    // 1, 0.5, 0.25
    @Comment("휴가 시간(하루, 반차, 반반차)")
    private double duration;

    @Comment("휴가 시작일")
    private LocalDate startDate;

    @Comment("휴가 종료일")
    private LocalDate endDate;
}
