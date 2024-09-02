package mightytony.sideproject.dayoffmanager.dayoff.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import mightytony.sideproject.dayoffmanager.dayoff.service.DayOffService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/dayoff")
@Tag(name = "휴가", description = "휴가 관련 api / 로그인 필요")
@PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_LEADER', 'ADMIN')")
public class DayOffController {

    private final DayOffService dayOffService;

    @Operation(summary = "휴가 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created")
    })
    @PostMapping("/register")
    public ResponseEntity<BasicResponse<Void>> dayOffRegister(@RequestBody DayOffApplyRequestDto requestDto, HttpServletRequest request) {

        dayOffService.requestDayOff(requestDto, request);

        return ResponseUtil.ok();
    }

    @Operation(summary = "휴가 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @DeleteMapping("/{companyId}/{userId}/dayoffs/{dayoffId}")
    public ResponseEntity<BasicResponse<Void>> deleteApplyDayOff(
            @PathVariable("companyId") Long companyId,
            @PathVariable("userId") String userId,
            @PathVariable("dayoffId") Long dayoffId
    ) {

        dayOffService.deleteDayOff(companyId, userId, dayoffId);

        return ResponseUtil.ok();
    }

    @Operation(summary = "휴가 신청 수정(대기 상태 일때 만)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @PatchMapping("/{companyId}/{userId}/dayoffs/{dayoffId}")
    public ResponseEntity<BasicResponse<Void>> updateDayOffInformation(
                HttpServletRequest request,
                Long companyId,
                String userId,
                Long dayoffId,
                DayOffUpdateRequestDto dto) {

        dayOffService.updateDayOffInfo(request, companyId, userId, dayoffId, dto);

        return ResponseUtil.ok();
    }

    @Operation(summary = "본인 휴가 신청 히스토리 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/{companyId}/{userId}")
    public ResponseEntity<BasicResponse<Page<DayOffApplyResponseDto>>> getMyDayOffs(
            @PathVariable("companyId") Long companyId,
            @PathVariable("userId") String userId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<DayOffApplyResponseDto> dayoffs = dayOffService.getMyDayOffs(companyId, userId, request, page, size);

        return ResponseUtil.ok(dayoffs);
    }

    /**
     * 본인 휴가 현황 상세 조회 페이지 테이블(년도, 월 1~12, 월 별 사용 휴가 개수, 총 남은 휴가 개수)
     */



    @Operation(summary = "모든 휴가 신청 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping("/{companyId}/list")
    public ResponseEntity<BasicResponse<Page<DayOffApplyResponseDto>>> getAllDayOff(
            @PathVariable("companyId") Long companyId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<DayOffApplyResponseDto> dayoffs = dayOffService.getAllDayOff(companyId, request, page, size);

        return ResponseUtil.ok(dayoffs);
    }

    /**
     * 관리자 - 휴가 신청 승인 / 반려
     */
}
