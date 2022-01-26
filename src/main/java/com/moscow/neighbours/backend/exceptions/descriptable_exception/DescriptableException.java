package com.moscow.neighbours.backend.exceptions.descriptable_exception;

public class DescriptableException extends RuntimeException {

    public final ExceptionDescription description;

    public DescriptableException(String message, ExceptionDescription description) {
        super(message);
        this.description = description;
    }

}
