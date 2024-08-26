package mightytony.sideproject.dayoffmanager.dayoff.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.dayoff.domain.QDayOff;
import mightytony.sideproject.dayoffmanager.dayoff.repository.query.QueryDayOffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryDayOffRepositoryImpl implements QueryDayOffRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DayOff> findMyAllDayOffHistory(Long companyId, String userId, Pageable pageable) {
        QDayOff qDayOff = QDayOff.dayOff;
        List<DayOff> results = queryFactory
                .selectFrom(qDayOff)
                .where(qDayOff.member.company.id.eq(companyId)
                        .and(qDayOff.member.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qDayOff.startDate.desc())
                .fetch();

        long total = queryFactory
                .selectFrom(qDayOff)
                .where(qDayOff.member.company.id.eq(companyId)
                        .and(qDayOff.member.userId.eq(userId)))
                //.fetchCount();
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }
}
