package com.studentrecords.model;

/**
 * Student model class demonstrating Encapsulation.
 * All fields are private with controlled access via getters/setters.
 */
public class Student implements Comparable<Student> {

    private int id;
    private String name;
    private String branch;
    private int year;
    private double cgpa;
    private String email;

    // Constructor
    public Student(int id, String name, String branch, int year, double cgpa, String email) {
        this.id     = id;
        this.name   = name;
        this.branch = branch;
        this.year   = year;
        this.cgpa   = cgpa;
        this.email  = email;
    }

    // --- Getters ---
    public int    getId()     { return id; }
    public String getName()   { return name; }
    public String getBranch() { return branch; }
    public int    getYear()   { return year; }
    public double getCgpa()   { return cgpa; }
    public String getEmail()  { return email; }

    // --- Setters ---
    public void setName(String name)     { this.name = name; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setYear(int year)        { this.year = year; }
    public void setCgpa(double cgpa)     { this.cgpa = cgpa; }
    public void setEmail(String email)   { this.email = email; }

    /**
     * Implements Comparable for natural ordering by student ID.
     * Demonstrates use of Comparable interface (Polymorphism).
     */
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Serialize student to a CSV line for file storage.
     */
    public String toCSV() {
        return id + "," + name + "," + branch + "," + year + "," + cgpa + "," + email;
    }

    /**
     * Deserialize a CSV line back into a Student object.
     */
    public static Student fromCSV(String line) {
        String[] parts = line.split(",");
        return new Student(
            Integer.parseInt(parts[0].trim()),
            parts[1].trim(),
            parts[2].trim(),
            Integer.parseInt(parts[3].trim()),
            Double.parseDouble(parts[4].trim()),
            parts[5].trim()
        );
    }

    @Override
    public String toString() {
        return String.format(
            "| %-4d | %-20s | %-6s | Yr %-1d | CGPA %-4.2f | %-25s |",
            id, name, branch, year, cgpa, email
        );
    }
}
