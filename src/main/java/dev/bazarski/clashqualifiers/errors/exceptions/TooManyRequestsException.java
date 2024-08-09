package dev.bazarski.clashqualifiers.errors.exceptions;

import dev.bazarski.clashqualifiers.errors.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class TooManyRequestsException extends RuntimeException {
    private HttpStatusCode status;
    private String message;

    public TooManyRequestsException(HttpStatusCode httpStatus) {
        super();
        this.status = httpStatus;
        this.message = ErrorMessages.TOO_MANY_REQUESTS;
    }

    public Integer getStatus() {
        return status.value();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
