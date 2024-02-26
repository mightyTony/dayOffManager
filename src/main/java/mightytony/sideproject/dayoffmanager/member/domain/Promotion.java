package mightytony.sideproject.dayoffmanager.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Promotion extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Comment("요청 한 멤버")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @Comment("요청 기업")
    private Company company;

}
