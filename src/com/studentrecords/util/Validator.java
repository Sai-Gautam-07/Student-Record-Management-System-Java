package com.studentrecords.util;

import com.studentrecords.exception.InvalidInputException;

/**
 * Utility class for validating user inputs before processing.
 */
public class Validator {

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        if (name.trim().length() < 2) {
            throw new InvalidInputException("Name must be at least 2 characters.");
        }
    }

    public static void validateYear(int year) throws InvalidInputException {
        if (year < 1 || year > 4) {
            throw new InvalidInputException("Year must be between 1 and 4.");
        }
    }

    public static void validateCgpa(double cgpa) throws InvalidInputException {
        if (cgpa < 0.0 || cgpa > 10.0) {
            throw new InvalidInputException("CGPA must be between 0.0 and 10.0.");
        }
    }

    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Please enter a valid email address.");
        }
    }

    public static void validateId(int id) throws InvalidInputException {
        if (id <= 0) {
            throw new InvalidInputException("Student ID must be a positive integer.");
        }
    }
}
