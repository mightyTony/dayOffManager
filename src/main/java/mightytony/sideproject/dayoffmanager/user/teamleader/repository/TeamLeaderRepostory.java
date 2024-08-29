package mightytony.sideproject.dayoffmanager.user.teamleader.repository;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.user.teamleader.repository.query.QueryTeamLeaderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamLeaderRepostory extends JpaRepository<Member, Long>, QueryTeamLeaderRepository {
}
