package mightytony.sideproject.dayoffmanager.admin.repository;

import mightytony.sideproject.dayoffmanager.admin.repository.query.QueryAdminRepository;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Member, Long>, QueryAdminRepository {
}
