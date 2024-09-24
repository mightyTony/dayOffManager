package mightytony.sideproject.dayoffmanager.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import mightytony.sideproject.dayoffmanager.auth.domain.dto.request.MemberUpdateRequestDto;
import mightytony.sideproject.dayoffmanager.common.domain.BaseTimeEntity;
import mightytony.sideproject.dayoffmanager.company.domain.Company;
import mightytony.sideproject.dayoffmanager.company.domain.Department;
import mightytony.sideproject.dayoffmanager.dayoff.domain.DayOff;
import mightytony.sideproject.dayoffmanager.user.admin.domain.dto.request.AdminMemberUpdateRequestDto;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 유저 테이블
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@SQLDelete(sql = "UPDATE Member SET deleted = true WHERE id = ?")
@SQLDelete(sql = "UPDATE Member SET deleted = true WHERE member_id= ?")
@Where(clause = "deleted = false")
@Builder
public class Member extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayOff> dayOffs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @Comment("부서")
    private Department department;

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    @Comment("이름")
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image")
    @Builder.Default
    private String profileImage = "default.jpg";

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE; // 기본 값을 False로 설정

    @Column(name = "deleted_date")
    private LocalDate deleteDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    @Builder.Default
    private List<MemberRole> roles = new ArrayList<>(Arrays.asList(MemberRole.EMPLOYEE));

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "hire_date")
    @Builder.Default
    private LocalDate hireDate = LocalDate.now();

    @Column(name = "resignation_date")
    @Builder.Default
    private LocalDate resignationDate = null;

    @Column(name = "dayoff_count", nullable = false)
    @Builder.Default
    private double dayOffCount = 0.0;

    //근속에 따른 추가 월 마다 추가 휴가
    @Column(name = "plus_dayoff", nullable = false)
    @Builder.Default
    private int plusDayOff = 0;

    // 회사 등록 승인
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Comment("회사 등록 승인")
    @Builder.Default
    private MemberStatus status = MemberStatus.PENDING;

//    // 사원 -> 어드민(회사 관리자) 업데이트
//    public void updateToAdmin() {
//        this.roles = Collections.singletonList(MemberRole.ADMIN);
//    }
//
//    // 사원 -> 팀장
//    public void updateToTeamLeader() {
//        this.roles = Collections.singletonList(MemberRole.TEAM_LEADER);
//    }
//
//    public void settingDayOff(double dayOffCount) {
//        this.dayOffCount = dayOffCount;
//    }
    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void welcome(String employeeNumber, Department department) {
        this.status = MemberStatus.APPROVED;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }

    public void updateInformation(MemberUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.profileImage = requestDto.getProfileImage();
    }

    public void updateFromAdmin(AdminMemberUpdateRequestDto requestDto,  Department department) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.profileImage = requestDto.getProfileImage();
        this.department = department;
        this.roles = new ArrayList<>(requestDto.getRoles());
        this.dayOffCount = requestDto.getDayOffCount();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
        this.deleteDate = LocalDate.now();
        this.resignationDate = LocalDate.now();
    }
}
