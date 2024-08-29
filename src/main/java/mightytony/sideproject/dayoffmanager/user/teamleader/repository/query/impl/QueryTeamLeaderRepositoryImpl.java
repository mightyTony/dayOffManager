package mightytony.sideproject.dayoffmanager.user.teamleader.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.QMember;
import mightytony.sideproject.dayoffmanager.user.teamleader.repository.query.QueryTeamLeaderRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryTeamLeaderRepositoryImpl implements QueryTeamLeaderRepository {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;
}
