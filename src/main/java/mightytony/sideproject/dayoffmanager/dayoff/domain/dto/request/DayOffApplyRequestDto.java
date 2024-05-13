package mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;

// 휴가 신청 요청 DTO
@Data
@Schema(description = "휴가 등록 dto")
public class DayOffApplyRequestDto {

    @NotNull
    private DayOffType dayOffType;
    @NotNull
    @Schema(description = "시간", example = "8")
    private Integer duration;
}
