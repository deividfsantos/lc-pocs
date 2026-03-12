package com.dsantos.exception;

/**
 * Thrown when a meeting is booked for a participant who already has a conflicting meeting.
 */
public class ConflictException extends CalendarException {

    public ConflictException(String message) {
        super(message);
    }
}

