package mightytony.sideproject.dayoffmanager.company.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.response.MemberLoginResponseDto;
import mightytony.sideproject.dayoffmanager.auth.service.impl.AuthServiceImpl;
import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.company.repository.DepartmentRepository;
import mightytony.sideproject.dayoffmanager.company.service.DepartmentService;
import mightytony.sideproject.dayoffmanager.config.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AuthServiceImpl authService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public List<Department> getDepartmentsByCompany(HttpServletRequest request) {
        String userId = authService.isThatYou(request);
        MemberLoginResponseDto cachedUserInformation = jwtTokenProvider.getCachedUserInformation(userId);
        Long companyId = cachedUserInformation.getCompanyId();

        return departmentRepository.findAllByCompanyId(companyId);
    }
}
