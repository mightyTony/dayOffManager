package mightytony.sideproject.dayoffmanager.user.master.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.user.master.domain.dto.request.MasterInviteAdminRequestDto;
import mightytony.sideproject.dayoffmanager.user.master.service.MasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 마스터(홈페이지 관리자)
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/master")
@Tag(name = "마스터(웹마스터)", description = "마스터 관련 api / 마스터 권한 필요")
public class MasterController {
    private final MasterService masterService;

    @Operation(summary = "관리자 권한 부여", description = "유저에게 회사 관리자 권한 부여를 해줍니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS",
                content = @Content(schema = @Schema(implementation = MasterInviteAdminRequestDto.class))
            )
    })
    @PostMapping("/invitation")
    public ResponseEntity<BasicResponse<String>> inviteAdminToCompany(@Valid @RequestBody MasterInviteAdminRequestDto dto) {
        String ret = masterService.inviteAdminToCompany(dto);

        return ResponseUtil.ok(ret);
    }


}
