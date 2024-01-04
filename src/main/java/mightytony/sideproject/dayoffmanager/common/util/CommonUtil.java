package mightytony.sideproject.dayoffmanager.common.util;

import java.util.UUID;

public class CommonUtil {

    /**
     * @apiNote : 랜덤 이름 생성 메서드 / ex) 이미지 파일 이름 난수 생성
     */
    public static String makeUUID() {
        return UUID.randomUUID().toString();
    }
}
