package mightytony.sideproject.dayoffmanager.dayoff.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.dayoff.repository.query.QueryDayOffRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryDayOffRepositoryImpl implements QueryDayOffRepository{

    private final JPAQueryFactory queryFactory;

}
