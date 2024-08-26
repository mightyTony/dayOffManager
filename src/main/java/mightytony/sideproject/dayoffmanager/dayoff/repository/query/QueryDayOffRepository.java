package mightytony.sideproject.dayoffmanager.dayoff.repository.query;

import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDayOffRepository {
    Page<DayOff> findMyAllDayOffHistory(Long companyId, String userId, Pageable pageable);
}
