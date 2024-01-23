package mightytony.sideproject.dayoffmanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private final ExceptionStatus exceptionStatus;
}
