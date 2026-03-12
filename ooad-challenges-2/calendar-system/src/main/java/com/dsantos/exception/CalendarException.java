package com.dsantos.exception;

/**
 * Base unchecked exception for all domain-level calendar errors.
 */
public class CalendarException extends RuntimeException {

    public CalendarException(String message) {
        super(message);
    }

    public CalendarException(String message, Throwable cause) {
        super(message, cause);
    }
}

