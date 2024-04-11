package mightytony.sideproject.dayoffmanager.auth.repository;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.repository.query.QueryAuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long>, QueryAuthRepository {

    //Optional<Member> findByName(String name);

    Optional<Member> findByUserId(String userId);

    //boolean findByUserIdAndPhoneNumber(String userId, String phoneNumber);

    //boolean existsMemberByUserIdOrPhoneNumberOrEmail(String userId, String phoneNumber, String email);

    boolean existsMemberByUserId(String userId);
    boolean existsMemberByEmail(String email);
    boolean existsMemberByPhoneNumber(String phoneNumber);

}
