package mightytony.sideproject.dayoffmanager.company.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 1241435562L;

    public static final QCompany company = new QCompany("company");

    public final StringPath brandName = createString("brandName");

    public final StringPath businessNumber = createString("businessNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath primaryRepresentName1 = createString("primaryRepresentName1");

    public final StringPath startDate = createString("startDate");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

