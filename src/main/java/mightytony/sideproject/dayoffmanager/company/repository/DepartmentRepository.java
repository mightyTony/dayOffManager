package mightytony.sideproject.dayoffmanager.company.repository;

import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryDepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, QueryDepartmentRepository {
    @Query("SELECT d.name FROM Department d WHERE d.company.id = :companyId AND d.deleted = false")
    List<String> findDepartmentNamesByCompanyId(@Param("companyId") Long companyId);

    Department findDepartmentByCompany_IdAndName(Long companyId, String name);

    boolean existsDepartmentByCompany_IdAndName(Long companyId, String name);
}
