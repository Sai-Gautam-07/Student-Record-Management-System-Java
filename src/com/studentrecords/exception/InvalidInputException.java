package com.studentrecords.exception;

/**
 * Thrown when user provides invalid input (e.g. CGPA out of range, empty name).
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
