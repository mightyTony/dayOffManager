package mightytony.sideproject.dayoffmanager.company.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.QDepartment;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryDepartmentRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryDepartmentRepositoryImpl implements QueryDepartmentRepository {

    private final JPAQueryFactory queryFactory;
    private final QDepartment qDepartment = QDepartment.department;
}
