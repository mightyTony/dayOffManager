package mightytony.sideproject.dayoffmanager.company.repository;

import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryDepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, QueryDepartmentRepository {
    List<Department> findAllByCompanyId(Long companyId);

    Department findDepartmentByCompany_IdAndName(Long companyId, String name);

    boolean existsDepartmentByCompany_IdAndName(Long companyId, String name);
}
