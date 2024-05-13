package mightytony.sideproject.dayoffmanager.dayoff.schedule;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.domain.Member;
import mightytony.sideproject.dayoffmanager.auth.domain.MemberStatus;
import mightytony.sideproject.dayoffmanager.auth.repository.AuthRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyDayOffIncrement {

    private final AuthRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

//    @Scheduled(cron = "0 0 3 1 1 *") // 매년 1월 1일 0시 0분에 실행
//    //@Scheduled(fixedRate = 30000) // 매 10초 마다 실행
//    @Transactional
//    public void updateDayOffCount() {
//        List<Member> allMember = memberRepository.findAll();
//
//        for (Member member : allMember) {
//            int yearsWorked = calculateYearsWorked(member);
//            double dayOffs = 15.0;
//
//            // 3년 이상 근속한 경우 +1
//            if(yearsWorked >= 3) {
//                // TODO 나중에 커스텀 하고 싶은 경우 고려..
//                // 3년 이상 근속한 후 1년 마다 휴가 1일 씩 추가
//                dayOffs += (yearsWorked - 2);
//            }
//
//            member.settingDayOff(dayOffs);
//            memberRepository.save(member);
//
//            log.info("휴가 스케쥴링 member = {}", member.getName());
//        }
//    }

//    private int calculateYearsWorked(Member member) {
//        LocalDate startDate = member.getCreatedDate();
//        LocalDate nowDate = LocalDate.now();
//
//        return (int) startDate.until(nowDate).toTotalMonths() / 12;
//    }

    private int calculateYearsWorked(Member member) {
        LocalDate startDate = member.getHireDate();
        LocalDate nowDate = LocalDate.now();

        return (int) startDate.until(nowDate).toTotalMonths() / 12;
    }

    private int calculateMonthsWorked(Member member) {
        LocalDate startDate = member.getHireDate();
        LocalDate nowDate = LocalDate.now();

        return (int) startDate.until(nowDate).toTotalMonths();
    }

    // 휴가 계산 로직
    private double calculateDayOffs(Member member) {
        int yearsWorked = calculateYearsWorked(member);
        double dayOffs = 15.0; // 기본 휴가

        if(yearsWorked < 1) {
            dayOffs = 12.0;
            int monthsWorked = calculateMonthsWorked(member);
            dayOffs -= monthsWorked;// 12 - 입사 월
        }

        // 3년 이상 근속한 경우
        if (yearsWorked >= 3) {
            dayOffs += Math.floor((yearsWorked - 2) / 2); // 3년 이상 근속 후 2년마다 추가 휴가
        }

        return dayOffs;
    }

    // 참고 https://velog.io/@gruzzimo/JPA-Modifying%EC%9D%98-flushAutomatically-%EC%98%B5%EC%85%98%EC%9D%80-%EC%96%B8%EC%A0%9C-%EC%93%B0%EC%A7%80
    @Scheduled(cron = "0 0 3 1 1 *") // 매년 1월 1일 오전 3시 0분에 스케쥴링
    //@Scheduled(fixedRate = 30000) // 매 10초 마다 실행 테스트용
    @Transactional
    @Modifying(clearAutomatically = true)
    public void updateDayOffCount() {
        List<Member> allMember = memberRepository.findAll();

        for (Member member : allMember) {
            // Member 가 회사에 등록이 되어 있는 경우 에만
            if(member.getStatus() == MemberStatus.APPROVED && member.getCompany() != null) {
                double dayOffs = calculateDayOffs(member);

                entityManager.createQuery("UPDATE Member m SET m.dayOffCount = :dayOffs WHERE m.id = :id AND m.status = :status")
                        .setParameter("dayOffs", dayOffs)
                        .setParameter("id", member.getId())
                        .setParameter("status", MemberStatus.APPROVED)
                        .executeUpdate();
            }
            //log.info("이름 : {} , 휴가 개수 = {} ",member.getName(), member.getDayOffCount());
            //log.info("휴가 스케쥴링 member = {}", member.getName());
        }
    }


}
