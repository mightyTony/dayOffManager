package mightytony.sideproject.dayoffmanager.company.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryCompanyRepositoryImpl {

    private final JPAQueryFactory queryFactory;
}
