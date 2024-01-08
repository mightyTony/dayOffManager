package mightytony.sideproject.dayoffmanager.repository;

import mightytony.sideproject.dayoffmanager.domain.member.Member;
import mightytony.sideproject.dayoffmanager.repository.query.QueryMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {

    Optional<Member> findByName(String name);
}
