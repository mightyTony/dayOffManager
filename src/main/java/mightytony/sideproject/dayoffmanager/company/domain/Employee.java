package mightytony.sideproject.dayoffmanager.company.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE Employee SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Employee extends Member {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long employee_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @Comment("회사")
    private Company company;

    @Column(name = "employee_number", unique = true)
    @Comment("사번")
    private String employeeNumber;

    @Column(name = "hire_date", updatable = false) //nullable = false
    @Comment("입사 날짜")
    private LocalDate hireDate = LocalDate.now();

    @Column(name = "resignation_date")
    @Comment("퇴사 날짜")
    private LocalDate resignationDate = null; // null = 재직 중

    @Column(name = "vacation_count", nullable = false)
    @Comment("휴가 개수")
    private double vacationCount = 15.0;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;
}

