package com.company.managementservice.exception;

public class ConstraintViolationException extends Exception {
    public ConstraintViolationException(String message) {
        super(message);
    }
}
