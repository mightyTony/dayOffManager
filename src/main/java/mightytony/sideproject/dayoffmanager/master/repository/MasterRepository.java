package mightytony.sideproject.dayoffmanager.master.repository;

import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.company.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Employee, Long> {

    //Member findMemberByUserId(String userId);
    boolean existsByEmail(String email);
}
