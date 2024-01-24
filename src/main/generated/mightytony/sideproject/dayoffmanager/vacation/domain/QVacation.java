package mightytony.sideproject.dayoffmanager.vacation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacation is a Querydsl query type for Vacation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacation extends EntityPathBase<Vacation> {

    private static final long serialVersionUID = -1549750980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacation vacation = new QVacation("vacation");

    public final NumberPath<Double> duration = createNumber("duration", Double.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final mightytony.sideproject.dayoffmanager.member.domain.QMember member;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final EnumPath<VacationType> vacationType = createEnum("vacationType", VacationType.class);

    public QVacation(String variable) {
        this(Vacation.class, forVariable(variable), INITS);
    }

    public QVacation(Path<? extends Vacation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacation(PathMetadata metadata, PathInits inits) {
        this(Vacation.class, metadata, inits);
    }

    public QVacation(Class<? extends Vacation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new mightytony.sideproject.dayoffmanager.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

