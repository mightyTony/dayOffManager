package mightytony.sideproject.dayoffmanager.dayoff.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.response.DayOffApplyResponseDto;
import org.springframework.data.domain.Page;

public interface DayOffService {

    void requestDayOff(DayOffApplyRequestDto requestDto, HttpServletRequest request);

    Page<DayOffApplyResponseDto> getAllDayOff(Long companyId, HttpServletRequest request, int page, int size);
}
