package mightytony.sideproject.dayoffmanager.dayoff.domain;

import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @Comment("휴가 시간(1~8)")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("심사 상태")
    private DayOffStatus status;

}
