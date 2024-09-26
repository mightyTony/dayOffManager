package mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffType;

import java.time.LocalDate;

// 휴가 신청 요청 DTO
@Data
@Schema(description = "휴가 등록 dto")
public class DayOffApplyRequestDto {

    @NotNull
    private DayOffType dayOffType;
    @NotNull
    @Schema(description = "휴가 시간", example = "0.5")
    private Double duration;

    @Schema(description = "휴가 시작일", example = "2024-01-01")
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "휴가 종료일", example = "2024-01-02")
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endDate;
}
