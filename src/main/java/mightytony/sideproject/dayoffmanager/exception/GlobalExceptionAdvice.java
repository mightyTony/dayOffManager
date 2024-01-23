package mightytony.sideproject.dayoffmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   // 전역에 예외처리 하는 어노테이션
public class GlobalExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ErrorDto(
                ex.getExceptionStatus().getMessage(),
                ex.getExceptionStatus().getStatusCode()),
                HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()));
    }
}
