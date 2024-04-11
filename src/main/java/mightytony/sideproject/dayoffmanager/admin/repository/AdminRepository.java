package mightytony.sideproject.dayoffmanager.admin.repository;

import mightytony.sideproject.dayoffmanager.admin.repository.query.QueryAdminRepository;
import mightytony.sideproject.dayoffmanager.company.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Employee, Long>, QueryAdminRepository {
}
