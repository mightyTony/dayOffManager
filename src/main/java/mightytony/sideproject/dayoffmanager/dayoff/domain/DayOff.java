package mightytony.sideproject.dayoffmanager.dayoff.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.dayoff.domain.dto.request.DayOffUpdateRequestDto;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE day_off SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class DayOff extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("휴가 고유 번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Comment("유저 ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("휴가 유형")
    private DayOffType type;

    // 1, 0.5, 0.25
    @Comment("휴가 시간(하루, 반차, 반반차)")
    private double duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("심사 상태")
    private DayOffStatus status;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @Comment("휴가 시작일")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Comment("휴가 종료일")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endDate;

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void update(DayOffUpdateRequestDto dto) {
        this.type = dto.getType();
        this.duration = dto.getDuration();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }

    public void ApproveOrReject(DayOffStatus status) {
        this.status = status;
    }

}
