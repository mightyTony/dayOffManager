package mightytony.sideproject.dayoffmanager.company.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import org.hibernate.annotations.Comment;

/**
 * 회사 테이블
 */
@Entity // jpa가 여기 작성된것을 테이블로 인식할 수 있도록 선언
@Builder  // builder 패턴
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // @Id : pk 설정, @GeneratedValue : 생성전략설정
    @Comment("고유 번호") // 컬럼 코멘트
    private Long id;

    @Comment("사업자등록번호")
    @Column(name = "b_no",nullable = false, unique = true, length = 10) // length : 컬럽 타입 길이 설정
    private String businessNumber;

    @Comment("개업일자 (YYYYMMDD 포맷)")
    @Column(name = "start_dt",nullable = false)
    private String startDate;

    @Comment("대표자성명1")
    @Column(name = "p_nm", nullable = false)
    private String primaryRepresentName1;

    @Comment("상표")
    @Column(name = "b_nm", nullable = false)
    private String brandName;

    // 양방향
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Member> members = new ArrayList<>();
}
