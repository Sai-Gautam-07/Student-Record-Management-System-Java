package com.studentrecords.exception;

/**
 * Thrown when attempting to add a student whose ID already exists.
 */
public class DuplicateStudentException extends Exception {
    public DuplicateStudentException(int id) {
        super("A student with ID " + id + " already exists.");
    }
}
