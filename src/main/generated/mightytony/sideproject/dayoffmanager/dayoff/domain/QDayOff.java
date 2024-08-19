package mightytony.sideproject.dayoffmanager.dayoff.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayOff is a Querydsl query type for DayOff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayOff extends EntityPathBase<DayOff> {

    private static final long serialVersionUID = 2111250120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayOff dayOff = new QDayOff("dayOff");

    public final mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity _super = new mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DatePath<java.time.LocalDate> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Double> duration = createNumber("duration", Double.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final mightytony.sideproject.dayoffmanager.auth.domain.QMember member;

    //inherited
    public final DatePath<java.time.LocalDate> modifiedDate = _super.modifiedDate;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final EnumPath<DayOffStatus> status = createEnum("status", DayOffStatus.class);

    public final EnumPath<DayOffType> type = createEnum("type", DayOffType.class);

    public QDayOff(String variable) {
        this(DayOff.class, forVariable(variable), INITS);
    }

    public QDayOff(Path<? extends DayOff> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayOff(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayOff(PathMetadata metadata, PathInits inits) {
        this(DayOff.class, metadata, inits);
    }

    public QDayOff(Class<? extends DayOff> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new mightytony.sideproject.dayoffmanager.auth.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

