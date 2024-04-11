package mightytony.sideproject.dayoffmanager.company.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberRole;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE Employee SET deleted = true WHERE employee_number = ?")
@Where(clause = "deleted = false")
@SuperBuilder
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
    @Builder.Default
    private LocalDate hireDate = LocalDate.now();

    @Column(name = "resignation_date")
    @Builder.Default
    @Comment("퇴사 날짜")
    private LocalDate resignationDate = null; // null = 재직 중

    @Column(name = "vacation_count", nullable = false)
    @Builder.Default
    @Comment("휴가 개수")
    private double vacationCount = 0.0;

    @Column(unique = true)
    private String email;

    @Builder.Default
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;


}

