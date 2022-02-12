package com.moscow.neighbours.backend.controllers.routes.service.exceptions;

import com.moscow.neighbours.backend.exceptions.BaseException;

public class FetchRoutesException extends BaseException {
    public FetchRoutesException(String message) {
        super(message);
    }
}
