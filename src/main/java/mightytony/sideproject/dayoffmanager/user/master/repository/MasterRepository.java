package mightytony.sideproject.dayoffmanager.user.master.repository;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Member, Long> {

    //Member findMemberByUserId(String userId);
    boolean existsByEmail(String email);
}
