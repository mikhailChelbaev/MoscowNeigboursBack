package com.moscow.neighbours.backend.controllers.auth.exceptions;

import com.moscow.neighbours.backend.exceptions.BaseException;

public class AccountConfirmationException extends BaseException {
    public AccountConfirmationException(String message) {
        super(message);
    }

    public AccountConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }
}
