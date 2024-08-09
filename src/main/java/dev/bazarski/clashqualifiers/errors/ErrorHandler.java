package dev.bazarski.clashqualifiers.errors;

import dev.bazarski.clashqualifiers.errors.exceptions.TooManyRequestsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(TooManyRequestsException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    private ErrorMessage handleException(TooManyRequestsException exception) {
        return new ErrorMessage(exception.getStatus(), exception.getMessage());
    }
}
