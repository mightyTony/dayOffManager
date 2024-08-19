package mightytony.sideproject.dayoffmanager.dayoff.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
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

    /**
     * 휴가 신청
     */
    @PostMapping("/register")
    public ResponseEntity<BasicResponse<Void>> dayOffRegister(@RequestBody DayOffApplyRequestDto requestDto, HttpServletRequest request) {

        dayOffService.requestDayOff(requestDto, request);

        return ResponseUtil.ok();
    }

    /**
     * 휴가 신청 삭제(대기 상태일때 만)
     */
    @DeleteMapping("/{companyId}/{userId}/dayoffs/{dayoffId}")
    public ResponseEntity<BasicResponse<Void>> deleteApplyDayOff(
            @PathVariable("companyId") Long companyId,
            @PathVariable("username") String username,
            @PathVariable("dayoffId") Long dayoffId
    ) {

        return ResponseUtil.ok();
    }

    /**
     * 사원 - 휴가 신청 수정(대기 상태일때 만)
     */



    /**
     * 본인 휴가 현황 상세 조회 페이지 테이블(년도, 월 1~12, 월 별 사용 휴가 개수, 총 남은 휴가 개수)
     */

    /**
     * 관리자 - 모든 휴가 신청 조회
     */
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

    /**
     * 팀장 - 자기 팀 모든 사원(팀원) 휴가 신청 조회
     */

    /**
     * 팀장 - 사원(팀원) 휴가 신청 승인 / 반려
     */
}
