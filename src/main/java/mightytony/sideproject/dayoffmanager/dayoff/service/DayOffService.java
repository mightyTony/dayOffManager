package mightytony.sideproject.dayoffmanager.dayoff.service;

import jakarta.servlet.http.HttpServletRequest;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffApplyRequestDto;

public interface DayOffService {

    void requestDayOff(DayOffApplyRequestDto requestDto, HttpServletRequest request);
}
