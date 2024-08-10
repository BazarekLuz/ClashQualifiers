package dev.bazarski.clashqualifiers.errors.exceptions;

import dev.bazarski.clashqualifiers.errors.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AccountNotFoundException extends RuntimeException {
    private HttpStatusCode status;
    private String message;

    public AccountNotFoundException() {
        super();
        this.status = HttpStatus.NOT_FOUND;
        this.message = ErrorMessages.ACCOUNT_NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
