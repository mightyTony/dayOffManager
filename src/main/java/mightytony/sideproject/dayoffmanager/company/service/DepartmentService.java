package mightytony.sideproject.dayoffmanager.company.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DepartmentService {

    List<String> getDepartmentsByCompany(HttpServletRequest request, Long companyId);
}
