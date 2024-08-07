package mightytony.sideproject.dayoffmanager.user.admin.repository.query.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.user.admin.repository.query.QueryAdminRepository;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.domain.QMember;
import mightytony.sideproject.dayoffmanager.company.domain.QCompany;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryAdminRepositoryImpl implements QueryAdminRepository {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;

    @Override
    public Member findMemberByUserIdAndCompanyId(String userId, Long companyId) {
        return queryFactory.selectFrom(member)
                .innerJoin(member.company, QCompany.company).fetchJoin()
                .where(member.userId.eq(userId)
                        .and(QCompany.company.id.eq(companyId))
                        .and(member.deleted.eq(false))
                        .and(member.status.eq(MemberStatus.PENDING))
                )
                .fetchOne();
    }

}
