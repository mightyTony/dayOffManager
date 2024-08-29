package mightytony.sideproject.dayoffmanager.user.teamleader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import mightytony.sideproject.dayoffmanager.user.teamleader.service.TeamLeaderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/teamleader")
@Tag(name = "팀장", description = "팀장 관련 api / 로그인 필요")
@PreAuthorize("hasAnyRole('TEAM_LEADER')")
@RequiredArgsConstructor
public class TeamLeaderController {

    private final TeamLeaderService teamLeaderService;

    /**
     * 팀장 - 자기 팀 모든 사원(팀원) 휴가 신청 조회
     */
    @Operation(summary = "자기 팀(부서) 모든 팀원 휴가 신청 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/comapany/{companyId}/departments/{departmentId}/list")
    public ResponseEntity<BasicResponse<Void>> getMyTeamDayOffs(@PathVariable Long companyId,
                                                                @PathVariable Long departmentId,
                                                                HttpServletRequest request,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {

        Page<DayOffApplyResponseDto> dayoffs = teamLeaderService.getMyTeamDayOffs(companyId,departmentId,request,page,size);

        return ResponseUtil.ok();
    }

    /**
     * 팀장 - 사원(팀원) 휴가 신청 승인 / 반려
     */
    @Operation(summary = "자기 팀(부서) 팀원 휴가 신청 승인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @PutMapping("/company/{companyId}/departments/{departmentId}/{userId}/dayoffs/{dayOffId}")
    public ResponseEntity<BasicResponse<Void>> processDayOffRequest(@PathVariable Long companyId,
                                                                    @PathVariable Long departmentId,
                                                                    @PathVariable String userId,
                                                                    @PathVariable Long dayOffId,
                                                                    DayOffStatus dayOffStatus,
                                                                    HttpServletRequest request) {

        teamLeaderService.processDayOffRequest(companyId, departmentId, userId, dayOffId, dayOffStatus, request);

        return ResponseUtil.ok();
    }

}
