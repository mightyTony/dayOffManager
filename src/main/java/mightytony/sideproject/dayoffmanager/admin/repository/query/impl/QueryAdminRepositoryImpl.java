package mightytony.sideproject.dayoffmanager.admin.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.admin.repository.query.QueryAdminRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryAdminRepositoryImpl implements QueryAdminRepository {

    private final JPAQueryFactory queryFactory;
}
