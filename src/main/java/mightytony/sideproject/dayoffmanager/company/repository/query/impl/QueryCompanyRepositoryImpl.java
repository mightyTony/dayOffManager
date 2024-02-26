package mightytony.sideproject.dayoffmanager.company.repository.query.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyRequestDto;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryCompanyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static mightytony.sideproject.dayoffmanager.company.domain.QCompany.company;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryCompanyRepositoryImpl implements QueryCompanyRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Company findByConditions(CompanyRequestDto req) {
        BooleanBuilder where = this.searchWhere(req);

        return queryFactory
                .selectFrom(company)
                .where(
                        company.brandName.eq(req.getBrandName()),
                        company.businessNumber.eq(req.getBusinessNumber()),
                        company.primaryRepresentName1.eq(req.getPrimaryRepresentName1()),
                        company.deleteYn.eq("N")
                )
                .fetchOne();
    }

    private BooleanBuilder searchWhere(CompanyRequestDto req) {

        BooleanBuilder builder = new BooleanBuilder();

        // 삭제 여부
        builder.and(company.deleteYn.eq("N"));

        // 검색 필터
        // if(!StringUtils.isEmpty(req.getBusinessNumber())) {  isEmpty is Deprecated !!
        if(!StringUtils.hasText(req.getBusinessNumber())){
            builder.and(company.businessNumber.eq(req.getBusinessNumber()));
        }
        if(!StringUtils.hasText(req.getPrimaryRepresentName1())) {
            builder.and(company.primaryRepresentName1.eq(req.getPrimaryRepresentName1()));
        }
        if(!StringUtils.hasText(req.getBrandName())) {
            builder.and(company.brandName.eq(req.getBrandName()));
        }

        return builder;
    }
}
