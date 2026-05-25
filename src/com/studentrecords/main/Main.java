package com.studentrecords.main;

import com.studentrecords.exception.DuplicateStudentException;
import com.studentrecords.exception.InvalidInputException;
import com.studentrecords.exception.StudentNotFoundException;
import com.studentrecords.model.Student;
import com.studentrecords.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point - console-based menu UI.
 * Handles all user interaction; delegates all logic to StudentService.
 *
 * Demonstrates:
 *  - Clean separation of UI and business logic
 *  - try-catch blocks for every checked exception
 *  - NumberFormatException handling for bad Scanner input
 */
public class Main {

    private static final StudentService service = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAll();
                    break;
                case 3:
                    searchById();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    filterByBranch();
                    break;
                case 6:
                    filterByYear();
                    break;
                case 7:
                    updateStudent();
                    break;
                case 8:
                    deleteStudent();
                    break;
                case 9:
                    service.printStats();
                    break;
                case 0:
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        try {
            int id = readInt("Student ID   : ");
            String name = readString("Name         : ");
            String branch = readString("Branch (e.g. CSE): ");
            int year = readInt("Year (1-4)   : ");
            double cgpa = readDouble("CGPA (0-10)  : ");
            String email = readString("Email        : ");

            service.addStudent(id, name, branch, year, cgpa, email);
        } catch (DuplicateStudentException | InvalidInputException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void viewAll() {
        List<Student> all = service.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("\nNo records found.");
            return;
        }
        System.out.println("\n--- All Students (" + all.size() + ") ---");
        printHeader();
        all.forEach(System.out::println);
        printDivider();
    }

    private static void searchById() {
        int id = readInt("\nEnter Student ID: ");
        try {
            Student s = service.getById(id);
            printHeader();
            System.out.println(s);
            printDivider();
        } catch (StudentNotFoundException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void searchByName() {
        String keyword = readString("\nEnter name keyword: ");
        List<Student> results = service.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No students found matching \"" + keyword + "\".");
        } else {
            printHeader();
            results.forEach(System.out::println);
            printDivider();
        }
    }

    private static void filterByBranch() {
        String branch = readString("\nEnter branch (e.g. CSE): ");
        List<Student> results = service.getByBranch(branch);
        if (results.isEmpty()) {
            System.out.println("No students found in branch: " + branch);
        } else {
            System.out.println("\n--- Branch: " + branch.toUpperCase() + " (" + results.size() + ") ---");
            printHeader();
            results.forEach(System.out::println);
            printDivider();
        }
    }

    private static void filterByYear() {
        int year = readInt("\nEnter year (1-4): ");
        List<Student> results = service.getByYear(year);
        if (results.isEmpty()) {
            System.out.println("No students found in year " + year + ".");
        } else {
            System.out.println("\n--- Year " + year + " Students (" + results.size() + ") ---");
            printHeader();
            results.forEach(System.out::println);
            printDivider();
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        int id = readInt("Enter Student ID to update: ");
        try {
            Student existing = service.getById(id);
            System.out.println("Current record:");
            printHeader();
            System.out.println(existing);
            printDivider();
            System.out.println("Enter new values (press Enter to keep current):\n");

            String name = readStringOptional("Name   [" + existing.getName() + "]: ", existing.getName());
            String branch = readStringOptional("Branch [" + existing.getBranch() + "]: ", existing.getBranch());
            int year = readIntOptional("Year   [" + existing.getYear() + "]: ", existing.getYear());
            double cgpa = readDoubleOptional("CGPA   [" + existing.getCgpa() + "]: ", existing.getCgpa());
            String email = readStringOptional("Email  [" + existing.getEmail() + "]: ", existing.getEmail());

            service.updateStudent(id, name, branch, year, cgpa, email);
        } catch (StudentNotFoundException | InvalidInputException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        int id = readInt("\nEnter Student ID to delete: ");
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes")) {
            try {
                service.deleteStudent(id);
            } catch (StudentNotFoundException e) {
                System.out.println("[Error] " + e.getMessage());
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void printBanner() {
        System.out.println("+------------------------------------------+");
        System.out.println("|     Student Record Management System     |");
        System.out.println("|          Developed by: Sai Gautam        |");
        System.out.println("+------------------------------------------+");
    }

    private static void printMenu() {
        System.out.println("\n+---------------------------------+");
        System.out.println("|              MENU               |");
        System.out.println("+---------------------------------+");
        System.out.println("|  1. Add Student                 |");
        System.out.println("|  2. View All Students           |");
        System.out.println("|  3. Search by ID                |");
        System.out.println("|  4. Search by Name              |");
        System.out.println("|  5. Filter by Branch            |");
        System.out.println("|  6. Filter by Year              |");
        System.out.println("|  7. Update Student              |");
        System.out.println("|  8. Delete Student              |");
        System.out.println("|  9. View Statistics             |");
        System.out.println("|  0. Exit                        |");
        System.out.println("+---------------------------------+");
    }

    private static void printHeader() {
        System.out.println("+------+----------------------+--------+------+----------+---------------------------+");
        System.out.println("|  ID  | Name                 | Branch |  Yr  |   CGPA   | Email                     |");
        System.out.println("+------+----------------------+--------+------+----------+---------------------------+");
    }

    private static void printDivider() {
        System.out.println("+------+----------------------+--------+------+----------+---------------------------+");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String readStringOptional(String prompt, String defaultVal) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultVal : input;
    }

    private static int readIntOptional(String prompt, int defaultVal) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultVal;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("[Warning] Invalid input, keeping current value.");
            return defaultVal;
        }
    }

    private static double readDoubleOptional(String prompt, double defaultVal) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultVal;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("[Warning] Invalid input, keeping current value.");
            return defaultVal;
        }
    }
}
