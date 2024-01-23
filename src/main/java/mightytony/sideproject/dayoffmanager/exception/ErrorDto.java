package mightytony.sideproject.dayoffmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private final String message;
    private final int StatusCode;
}
