package mightytony.sideproject.dayoffmanager.repository;

import mightytony.sideproject.dayoffmanager.domain.Member;
import mightytony.sideproject.dayoffmanager.repository.query.QueryMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {
}
