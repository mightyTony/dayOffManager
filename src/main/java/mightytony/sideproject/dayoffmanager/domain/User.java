package mightytony.sideproject.dayoffmanager.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 유저 테이블
 */
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(columnDefinition = "사번")
    private Integer employee_number;


    @Comment("이름")
    private String name;

    @Comment("직급")
    private String position;

    @Column(name = "phone_number")
    @Comment("휴대폰 번호")
    private String phoneNumber;

    @Column(name = "hire_date")
    @Comment("입사 날짜")
    private LocalDateTime hireDate;

    @Column(name = "resignation_date")
    @Comment("퇴사 날짜")
    private LocalDateTime resignationDate; // null = 재직 중
}
