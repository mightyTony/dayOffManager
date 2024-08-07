package mightytony.sideproject.dayoffmanager.user.master.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.user.master.repository.query.QueryMasterRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryMasterRepositoryImpl implements QueryMasterRepository {

    private final JPAQueryFactory queryFactory;
}
