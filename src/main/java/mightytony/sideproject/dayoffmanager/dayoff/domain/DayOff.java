package mightytony.sideproject.dayoffmanager.dayoff.domain;

import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

    // 반차면 4 = 0.5개 , 연차면 8 = 1.0개 휴가 감소, // 반반차 2 = 0.25
    @Comment("휴가 시간(1~8)")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("심사 상태")
    private DayOffStatus status;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

}
