package mightytony.sideproject.dayoffmanager.master.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 관리자 등록 요청을 위한 DTO
 */
@Data
@Schema(description = "관리자 등록 요청 dto")
public class MasterInviteAdminRequestDto {

    @NotNull
    @Schema(description = "유저 아이디", example = "admin")
    private String userId;
    @JsonProperty("b_nm")
    @Schema(description = "상호 명", example = "주식회사 티윈")
    private String brandName;
}
