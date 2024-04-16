package mightytony.sideproject.dayoffmanager.company.repository.query.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.company.domain.QCompany;
import mightytony.sideproject.dayoffmanager.company.repository.query.QueryCompanyRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Slf4j
@RequiredArgsConstructor
public class QueryCompanyRepositoryImpl implements QueryCompanyRepository {

    private final JPAQueryFactory queryFactory;
    private final QCompany qCompany = QCompany.company;

    @Override
    public void updateCompanyDeleteDate(String brandName) {
        queryFactory.update(qCompany)
                .set(qCompany.deleteDate, LocalDate.now())
                .where(qCompany.brandName.eq(brandName))
                .execute();
    }

//    @Override
//    public void deleteCompany(String brandName) {
//         queryFactory.update(qCompany)
//                .set(qCompany.deleteDate, LocalDate.now())
//                .where(qCompany.brandName.eq(brandName))
//                .execute();
//    }

    //private BooleanBuilder searchWhere()
}
