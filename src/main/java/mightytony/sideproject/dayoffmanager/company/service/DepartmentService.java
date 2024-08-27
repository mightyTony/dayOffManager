package mightytony.sideproject.dayoffmanager.company.service;

import mightytony.sideproject.dayoffmanager.company.domain.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getDepartmentsByCompany(Long companyId);
}