package mightytony.sideproject.dayoffmanager.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1869152068L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity _super = new mightytony.sideproject.dayoffmanager.common.domain.QBaseTimeEntity(this);

    public final mightytony.sideproject.dayoffmanager.company.domain.QCompany company;

    //inherited
    public final DatePath<java.time.LocalDate> createdDate = _super.createdDate;

    public final NumberPath<Double> dayOffCount = createNumber("dayOffCount", Double.class);

    public final ListPath<mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff, mightytony.sideproject.dayoffmanager.dayoff.domain.QDayOff> dayOffs = this.<mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff, mightytony.sideproject.dayoffmanager.dayoff.domain.QDayOff>createList("dayOffs", mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff.class, mightytony.sideproject.dayoffmanager.dayoff.domain.QDayOff.class, PathInits.DIRECT2);

    public final BooleanPath deleted = createBoolean("deleted");

    public final DatePath<java.time.LocalDate> deleteDate = createDate("deleteDate", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final StringPath employeeNumber = createString("employeeNumber");

    public final DatePath<java.time.LocalDate> hireDate = createDate("hireDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DatePath<java.time.LocalDate> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> plusDayOff = createNumber("plusDayOff", Integer.class);

    public final StringPath profileImage = createString("profileImage");

    public final DatePath<java.time.LocalDate> resignationDate = createDate("resignationDate", java.time.LocalDate.class);

    public final ListPath<MemberRole, EnumPath<MemberRole>> roles = this.<MemberRole, EnumPath<MemberRole>>createList("roles", MemberRole.class, EnumPath.class, PathInits.DIRECT2);

    public final EnumPath<MemberStatus> status = createEnum("status", MemberStatus.class);

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new mightytony.sideproject.dayoffmanager.company.domain.QCompany(forProperty("company")) : null;
    }

}

