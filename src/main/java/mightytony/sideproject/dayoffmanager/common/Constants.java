package mightytony.sideproject.dayoffmanager.common;

import java.time.Duration;

public final class Constants {

    //Access Token 만료기간 : 15~30분, Refresh Token 만료기간 : 3일 ~ 1달
    // https://convertlive.com/ko/u/%EB%B3%80%ED%99%98/%EB%B6%84/%EB%B0%9B%EB%8A%94-%EC%82%AC%EB%9E%8C/%EC%B4%88#15 계산기
    public static final long ACCESS_TOKEN_EXPIRED_TIME = 1 * 60 * 60 * 1000; // 1시간
    //public static final Duration ACCESS_TOKEN_EXPIRED_TIME = Duration.ofHours(1);
    public static final long REFRESH_TOKEN_EXPIRED_TIME = 14 * 24 * 60 * 60 * 1000; // 14일
    //public static final Duration REFRESH_TOKEN_EXPIRED_TIME = Duration.ofDays(14);
}
