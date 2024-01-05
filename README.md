# DayOffManager (연차 신청, 관리 시스템)
------------------ --

- ## 기술 스택 
    - Spring boot, Spring Security, Spring Data JPA, MySQL, Thymeleaf
    - JDK 17 이상 

- ## 인프라 (예정)
    - AWS Lamdba or ECS(EC2)
    - RDS
    - S3(? 유저 증명사진 저장 시 약관 필요 할 듯)
    - CloudFront
    - System Manager
    - GitHub, GitHubAction, CodeBuild, Codedeploy

- ## 구현 기능 (예정)
    - 공통
      - DB 스케쥴링
        - 1월 1일 시 기본 연차를 유저에게 부여 한다.
        - 다음 달 1일에 연차를 +1 개 모든 유저에게 부여 한다.(위의 전제랑 대립 됨)
    
    - 유저 
      - 회원가입
      - 로그인/로그아웃
      - 휴가 신청(연차 신청 양식 form 필요)
      - oAuth(네이버, 구글, 다음 아이디로 로그인 이런거 고려 해야함?)
      
    - 관리자 페이지
      - 휴가 승인/취소
        - 팀장 승인 시 / 프런트(관리자) 에서 승인을 하도록
      - 사원 휴가 신청 시 알람(슬랙 or 디스코드 or 이메일)
      - 사원 연차 페이지
        - 사원 목록 페이징
        - 연차 내역 기간별 설정, 이름별 설정 페이징
        - 달 마다 휴가 몇 개 썼는 지 보여야 함(색으로? 숫자로?)
          - 반차는 뭐 우째야 되노 
            - (연차 자료형을 소숫점으로 해야하나? 반반차 추가 고려 해야함?) 

-- -- 
[ API 명세서 ](API.md)

[ 요청 엑셀 파일 ](연차프로그램%20요청서.xlsx)
```
1. querydsl boot 3.0 설정 : https://docs.google.com/document/d/1j0jcJ9EoXMGzwAA2H0b9TOvRtpwlxI5Dtn3sRtuXQas/edit?pli=1#heading=h.vfy9wirpglmx
2. 스웨거 주소 : http://localhost:4860/swagger-ui/index.html#