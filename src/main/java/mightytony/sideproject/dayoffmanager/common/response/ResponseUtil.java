package mightytony.sideproject.dayoffmanager.common.response;

import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<BasicResponse<T>> ok(T body) {
        return new ResponseEntity<>(
                BasicResponse.res(ResponseCode.SUCCESS.getStatusCode(), ResponseCode.SUCCESS.getMessage(),
                        body
                ), HttpStatus.OK);
    }

    public static <T> ResponseEntity<BasicResponse<T>> ok(T body, int code, String msg) {
        return new ResponseEntity<>(
                BasicResponse.res(code, msg, body), HttpStatus.OK);
    }

    public static <T> ResponseEntity<BasicResponse<T>> ok() {
        return new ResponseEntity<>(
                BasicResponse.res(ResponseCode.SUCCESS.getStatusCode(), ResponseCode.SUCCESS.getMessage(), null), HttpStatus.OK);
    }
}
