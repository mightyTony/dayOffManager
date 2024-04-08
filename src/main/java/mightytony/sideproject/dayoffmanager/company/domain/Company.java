package mightytony.sideproject.dayoffmanager.company.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.dto.request.CompanyUpdateRequestDto;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 회사 테이블
 */
@Entity // jpa가 여기 작성된것을 테이블로 인식할 수 있도록 선언
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE Company SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Company  {
//extends BaseTimeEntity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // @Id : pk 설정, @GeneratedValue : 생성전략설정
    @Comment("고유 번호") // 컬럼 코멘트
    private Long id;

    @Comment("사업자등록번호")
    @Column(name = "bussiness_number",nullable = false, unique = true, length = 10) // length : 컬럽 타입 길이 설정
    private String businessNumber;

    @Comment("개업일자 (YYYYMMDD 포맷)")
    @Column(name = "start_date",nullable = false)
    private String startDate;

    @Comment("대표자성명1")
    @Column(name = "primary_represent_name", nullable = false)
    private String primaryRepresentName1;

    @Comment("상호 명")
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "delete_date")
    private LocalDate deleteDate;

    @Builder
    public Company(String businessNumber, String startDate, String primaryRepresentName1, String brandName) {
        this.businessNumber = businessNumber;
        this.startDate = startDate;
        this.primaryRepresentName1 = primaryRepresentName1;
        this.brandName = brandName;
    }

    //양방향
//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Employee> employees = new ArrayList<>();

//    public void delete() {
//        this.deleteYn = "Y";
//        this.deleteDate = LocalDate.now();
//    }

    public void update(CompanyUpdateRequestDto req) {
        this.brandName = req.getBrandName();
    }
}
