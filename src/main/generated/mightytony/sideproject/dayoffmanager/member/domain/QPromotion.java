package mightytony.sideproject.dayoffmanager.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotion is a Querydsl query type for Promotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotion extends EntityPathBase<Promotion> {

    private static final long serialVersionUID = -314034553L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotion promotion = new QPromotion("promotion");

    public final mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity _super = new mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity(this);

    public final mightytony.sideproject.dayoffmanager.company.domain.QCompany company;

    //inherited
    public final DatePath<java.time.LocalDate> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DatePath<java.time.LocalDate> modifiedDate = _super.modifiedDate;

    public QPromotion(String variable) {
        this(Promotion.class, forVariable(variable), INITS);
    }

    public QPromotion(Path<? extends Promotion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotion(PathMetadata metadata, PathInits inits) {
        this(Promotion.class, metadata, inits);
    }

    public QPromotion(Class<? extends Promotion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new mightytony.sideproject.dayoffmanager.company.domain.QCompany(forProperty("company")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

