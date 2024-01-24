package mightytony.sideproject.dayoffmanager.member.repository;

import mightytony.sideproject.dayoffmanager.member.domain.Member;
import mightytony.sideproject.dayoffmanager.member.repository.query.QueryMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {

    //Optional<Member> findByName(String name);

    Optional<Member> findByUserId(String userId);

    //boolean findByUserIdAndPhoneNumber(String userId, String phoneNumber);

    boolean existsMemberByUserIdAndPhoneNumber(String userId, String phoneNumber);
}
