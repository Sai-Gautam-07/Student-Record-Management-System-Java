package com.studentrecords.service;

import com.studentrecords.exception.DuplicateStudentException;
import com.studentrecords.exception.InvalidInputException;
import com.studentrecords.exception.StudentNotFoundException;
import com.studentrecords.model.Student;
import com.studentrecords.util.FileUtil;
import com.studentrecords.util.Validator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core service layer - all business logic lives here.
 *
 * Demonstrates:
 *  - HashMap for O(1) ID-based lookups
 *  - ArrayList for ordered iteration
 *  - Collections utility methods (sort, unmodifiable)
 *  - Custom Exception Handling (checked exceptions)
 *  - Separation of concerns (service vs. UI)
 */
public class StudentService {

    // Primary store: HashMap for fast ID lookups
    private final Map<Integer, Student> studentMap;

    public StudentService() {
        studentMap = new HashMap<>();
        // Load persisted data on startup
        List<Student> saved = FileUtil.loadStudents();
        for (Student s : saved) {
            studentMap.put(s.getId(), s);
        }
        System.out.println("[System] Loaded " + studentMap.size() + " record(s) from storage.");
    }

    // ------------------------------------------------------------------ ADD
    public void addStudent(int id, String name, String branch, int year,
                           double cgpa, String email)
            throws DuplicateStudentException, InvalidInputException {

        Validator.validateId(id);
        Validator.validateName(name);
        Validator.validateYear(year);
        Validator.validateCgpa(cgpa);
        Validator.validateEmail(email);

        if (studentMap.containsKey(id)) {
            throw new DuplicateStudentException(id);
        }

        Student student = new Student(id, name.trim(), branch.trim(), year, cgpa, email.trim());
        studentMap.put(id, student);
        persist();
        System.out.println("[Success] Student added: " + name);
    }

    // --------------------------------------------------------------- SEARCH
    public Student getById(int id) throws StudentNotFoundException {
        Student student = studentMap.get(id);
        if (student == null) throw new StudentNotFoundException(id);
        return student;
    }

    public List<Student> searchByName(String keyword) {
        String kw = keyword.toLowerCase().trim();
        return studentMap.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(kw))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Student> getByBranch(String branch) {
        return studentMap.values().stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch.trim()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Student> getByYear(int year) {
        return studentMap.values().stream()
                .filter(s -> s.getYear() == year)
                .sorted()
                .collect(Collectors.toList());
    }

    // --------------------------------------------------------------- UPDATE
    public void updateStudent(int id, String name, String branch, int year,
                              double cgpa, String email)
            throws StudentNotFoundException, InvalidInputException {

        Student student = getById(id); // throws StudentNotFoundException if not found

        Validator.validateName(name);
        Validator.validateYear(year);
        Validator.validateCgpa(cgpa);
        Validator.validateEmail(email);

        student.setName(name.trim());
        student.setBranch(branch.trim());
        student.setYear(year);
        student.setCgpa(cgpa);
        student.setEmail(email.trim());

        persist();
        System.out.println("[Success] Record updated for ID " + id);
    }

    // --------------------------------------------------------------- DELETE
    public void deleteStudent(int id) throws StudentNotFoundException {
        if (!studentMap.containsKey(id)) throw new StudentNotFoundException(id);
        studentMap.remove(id);
        persist();
        System.out.println("[Success] Student with ID " + id + " deleted.");
    }

    // ------------------------------------------------------------ LIST ALL
    public List<Student> getAllStudents() {
        List<Student> all = new ArrayList<>(studentMap.values());
        Collections.sort(all); // natural order by ID via Comparable
        return Collections.unmodifiableList(all);
    }

    // ------------------------------------------------------------ STATS
    public void printStats() {
        if (studentMap.isEmpty()) {
            System.out.println("No records available.");
            return;
        }
        OptionalDouble avg = studentMap.values().stream()
                .mapToDouble(Student::getCgpa).average();
        Student topper = Collections.max(studentMap.values(),
                Comparator.comparingDouble(Student::getCgpa));
        Student lowest = Collections.min(studentMap.values(),
                Comparator.comparingDouble(Student::getCgpa));

        System.out.println("  Total Students : " + studentMap.size());
        System.out.printf("  Average CGPA   : %.2f%n", avg.orElse(0));
        System.out.println("  Topper         : " + topper.getName() + " (CGPA " + topper.getCgpa() + ")");
        System.out.println("  Lowest CGPA    : " + lowest.getName() + " (CGPA " + lowest.getCgpa() + ")");

        // Branch-wise count using HashMap
        Map<String, Long> branchCount = new HashMap<>();
        for (Student s : studentMap.values()) {
            branchCount.merge(s.getBranch(), 1L, Long::sum);
        }
        System.out.println("  Branch Summary :");
        branchCount.forEach((b, c) -> System.out.println("    " + b + " : " + c + " student(s)"));
    }

    // ---------------------------------------------------- PRIVATE HELPERS
    private void persist() {
        FileUtil.saveStudents(new ArrayList<>(studentMap.values()));
    }

    public int count() {
        return studentMap.size();
    }
}
