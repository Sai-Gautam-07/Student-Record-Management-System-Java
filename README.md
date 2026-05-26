# Student Record Management System

A console-based CRUD application built in Core Java to manage student academic records.

## Features

- Add, view, update, and delete student records
- Search by student ID or name keyword
- Filter records by branch or academic year
- View statistics such as average CGPA, topper, lowest CGPA, and branch-wise count
- Persist records using CSV file I/O so data survives restarts
- Validate input with custom checked exceptions

## Concepts Demonstrated

| Concept | Where Used |
| --- | --- |
| OOP - Encapsulation | `Student.java` uses private fields with getters and setters |
| OOP - Polymorphism | `Student implements Comparable<Student>` |
| Collections - HashMap | `StudentService` uses O(1) ID lookups |
| Collections - ArrayList | Ordered listing and filtering results |
| Collections utilities | `Collections.sort()`, `Collections.max()`, `Collections.min()` |
| Exception handling | Three custom checked exceptions with UI-level try/catch handling |
| File I/O | `FileUtil.java` uses buffered CSV read/write |
| Stream API | Filtering, sorting, and averaging in `StudentService` |
| Separation of concerns | Model, service, utility, exception, and UI layers |

## Project Structure

```text
StudentRecordSystem/
|-- src/
|   `-- com/studentrecords/
|       |-- model/
|       |   `-- Student.java
|       |-- service/
|       |   `-- StudentService.java
|       |-- exception/
|       |   |-- StudentNotFoundException.java
|       |   |-- DuplicateStudentException.java
|       |   `-- InvalidInputException.java
|       |-- util/
|       |   |-- FileUtil.java
|       |   `-- Validator.java
|       `-- main/
|           `-- Main.java
|-- data/
|   `-- students.csv
|-- sources.txt
`-- README.md
```

`data/students.csv` is created automatically when the first record is saved.

## Prerequisites

- Java 8 or higher

## Compile

On macOS/Linux:

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

On Windows PowerShell:

```powershell
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Set-Content sources.txt
cmd /c "javac -d out @sources.txt"
```

## Run

```bash
java -cp out com.studentrecords.main.Main
```

## Sample Menu

```text
+------------------------------------------+
|     Student Record Management System     |
|          Developed by: Sai Gautam        |
+------------------------------------------+
[System] Loaded 0 record(s) from storage.

+---------------------------------+
|              MENU               |
+---------------------------------+
|  1. Add Student                 |
|  2. View All Students           |
|  3. Search by ID                |
|  4. Search by Name              |
|  5. Filter by Branch            |
|  6. Filter by Year              |
|  7. Update Student              |
|  8. Delete Student              |
|  9. View Statistics             |
|  0. Exit                        |
+---------------------------------+
```

## Tech Stack

- Language: Java
- Storage: CSV flat file
- Build: Plain `javac`, no Maven or Gradle required

---

Developed by [Sai Gautam Reddy Jetti](https://github.com/Sai-Gautam-07)
