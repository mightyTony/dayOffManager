package mightytony.sideproject.dayoffmanager.company.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.repository.DepartmentRepository;
import mightytony.sideproject.dayoffmanager.company.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
//    private final AuthServiceImpl authService;
//    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public List<String> getDepartmentsByCompany(HttpServletRequest request, Long companyId) {

        return departmentRepository.findDepartmentNamesByCompanyId(companyId);
    }
}
