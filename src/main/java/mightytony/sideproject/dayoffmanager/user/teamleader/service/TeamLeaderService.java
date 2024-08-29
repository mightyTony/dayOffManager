package mightytony.sideproject.dayoffmanager.user.teamleader.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import org.springframework.data.domain.Page;

public interface TeamLeaderService {
    void processDayOffRequest(Long companyId, Long departmentId, String userId, Long dayOffId, DayOffStatus status, HttpServletRequest request);

    Page<DayOffApplyResponseDto> getMyTeamDayOffs(Long companyId, Long departmentId, HttpServletRequest request, int page, int size);
}
