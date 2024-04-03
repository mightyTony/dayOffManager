package mightytony.sideproject.dayoffmanager.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> res(ResponseCode responseCode) {
        return res(responseCode.getStatusCode(), responseCode.getMessage());
    }

    public static <T> ApiResponse<T> res(final int responseCode, final String responseMessage) {
        return res(responseCode, responseMessage, null);
    }

    public static <T> ApiResponse<T> res(final int responseCode, final String responseMessage, final T t) {
        return ApiResponse.<T>builder()
                .data(t)
                .code(responseCode)
                .message(responseMessage)
                .build();
    }
}
