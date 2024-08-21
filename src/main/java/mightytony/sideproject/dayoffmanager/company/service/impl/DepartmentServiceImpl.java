package mightytony.sideproject.dayoffmanager.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.company.repository.DepartmentRepository;
import mightytony.sideproject.dayoffmanager.company.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartmentsByCompany(Long companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }
}
