package com.studentrecords.util;

import com.studentrecords.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all file I/O operations for persistent student data storage.
 * Data is stored in a plain CSV file: data/students.csv
 */
public class FileUtil {

    private static final String FILE_PATH = "data/students.csv";

    /**
     * Loads all student records from the CSV file into a List.
     * Returns an empty list if the file doesn't exist yet.
     */
    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    students.add(Student.fromCSV(line));
                }
            }
        } catch (IOException e) {
            System.out.println("[Warning] Could not read data file: " + e.getMessage());
        }

        return students;
    }

    /**
     * Saves the full list of students back to the CSV file.
     * Overwrites the file on every save (simple approach for a local system).
     */
    public static void saveStudents(List<Student> students) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student s : students) {
                writer.write(s.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("[Error] Could not save data: " + e.getMessage());
        }
    }
}
