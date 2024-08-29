package mightytony.sideproject.dayoffmanager.company.domain;

import jakarta.persistence.*;
import lombok.*;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE Department SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("부서 고유 번호")
    private Long id;

    @Comment("부서 명")
    @Column(name = "department_name", nullable = false, unique = true)
    private String name;

//    @Comment("부장 / 팀장 ")
//    @Column(name = "leader_name")
//    private String leaderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @Comment("회사")
    private Company company;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE; // 기본 값을 False로 설정
}
