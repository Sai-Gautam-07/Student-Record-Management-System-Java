package com.studentrecords.exception;

/**
 * Thrown when a student with a given ID is not found in the system.
 */
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(int id) {
        super("No student found with ID: " + id);
    }
}
