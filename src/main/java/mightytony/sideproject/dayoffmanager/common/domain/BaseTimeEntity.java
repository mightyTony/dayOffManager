package mightytony.sideproject.dayoffmanager.common.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}

/**
 * @MappedSuperClass : jpa entity 클래스들이 BaseTimeEntity를 상속 할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식 하게 한다.
 * @EntityListeners(AuditingEntityListener.class) : BaseTimeEntity 클래스에 Auditing 기능을 포함 시킨다.
 * @CreatedDate : Entity 생성 되어 저장될 때 시간이 자동 저장된다.
 * @LastModifiedDate : 조회한 Entity의 값을 변경 할 때 시간이 자동 저장된다.
 */

