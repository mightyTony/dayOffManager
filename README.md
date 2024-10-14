# DayOffManager (연차 신청, 관리 시스템)
------------------ --

- ## 기술 스택 
    - Spring boot, Spring Security, Spring Data JPA, MySQL, QueryDSL, Vue3, Pinia, axios
    - JDK 17 version 

- ## 인프라 
    - EC2
    - RDS
    - S3(유저 증명사진 저장)
    - CloudFront
    - GitHub, GitHubAction, Docker, docker-compose

- ## 구현 기능 
    - 공통
      - 스케쥴링
        - 모든 휴저에게 1월 1일에 근속에 따라 연차를 유저 에게 부여 한다.
    
    - 유저 
      - 회원가입
      - 로그인/로그아웃
      - 휴가 신청
      - oAuth(추후 기능 제공)
      - 등록된 기업 상호 명 조회
      - 기업 등록 (국세청 사업자등록번호 조회 OpenAPI 연동) 
      - 유저 정보 수정
      - 유저 프로필 사진 수정(S3+cloudfront)
      - 유저 비밀 번호 변경(추후 기능 제공)
      
    - 관리자 
      - 멤버 등록 승인/취소
        - 멤버 부서 등록 / 수정
        - 멤버 등록 신청 거절
      - 멤버 정보
        - 멤버 정보 조회
        - 멤버 정보 수정
        - 멤버 정보 삭제 (soft 삭제)
        - 멤버 정보 등록 / 사원 사번 부여 
      - 사원 휴가 신청 시 알람(추후 기능 제공)
      - 사원 연차 페이지
        - 회사 등록 요청 멤버 페이징 조회
        - 팀장 승인 - 어드민 승인 시 휴가 등록, 휴가 개수 차감
        - 모든 휴가 신청 조회
        - 휴가 신청 승인/반려
    
    - 팀장 페이지
      - 팀원 휴가 신청 조회
      - 팀원 휴가 신청 승인/반려
      - 팀원 정보 조회 페이징
        

-- -- 
[ API 명세서 ](API.md)

[ 요청 엑셀 파일 ](연차프로그램%20요청서.xlsx)
```
1. querydsl boot 3.0 설정 : https://docs.google.com/document/d/1j0jcJ9EoXMGzwAA2H0b9TOvRtpwlxI5Dtn3sRtuXQas/edit?pli=1#heading=h.vfy9wirpglmx
2. 스웨거 주소 : http://localhost:4860/swagger-ui/index.html#
3. security+jwt 참고 : https://suddiyo.tistory.com/category/Spring