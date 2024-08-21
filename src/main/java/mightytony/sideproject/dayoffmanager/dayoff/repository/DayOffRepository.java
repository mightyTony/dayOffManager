package mightytony.sideproject.dayoffmanager.dayoff.repository;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOffStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    //Page<DayOff> findByCompanyIdAndStatus(Long companyId, MemberStatus status, Pageable pageable);
    Page<DayOff> findByMember_Company_IdAndStatus(Long companyId, DayOffStatus status, Pageable pageable);

    Page<DayOff> findByMember_Company_IdAndMemberUserId(Long companyId, String userId, Pageable pageable);
}
