package mightytony.sideproject.dayoffmanager.dayoff.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.common.response.BasicResponse;
import mightytony.sideproject.dayoffmanager.common.response.ResponseUtil;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.service.DayOffService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 휴가 신청 수정(대기 상태일때 만)
     */

    /**
     * 관리자의 휴가 신청 조회
     */

    /**
     * 본인 휴가 현황 상세 조회 페이지 테이블(년도, 월 1~12, 월 별 사용 휴가 개수, 총 남은 휴가 개수)
     */
}
