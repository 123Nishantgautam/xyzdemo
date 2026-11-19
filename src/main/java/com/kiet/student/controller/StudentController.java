package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/students", "/student"})
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        // Sample seed data
        students.add(new Student(1L, "Alice Johnson", "alice.johnson@example.com", 20, "Computer Science"));
        students.add(new Student(2L, "Bob Smith", "bob.smith@example.com", 21, "Information Technology"));
        students.add(new Student(3L, "Charlie Brown", "charlie.brown@example.com", 22, "Electronics"));
    }

    // GET all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // GET student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return students.stream()
                .filter(s -> s.getId() != null && s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // POST - Create a new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        if (student.getId() == null) {
            long maxId = students.stream()
                    .mapToLong(s -> s.getId() != null ? s.getId() : 0L)
                    .max()
                    .orElse(0L);
            student.setId(maxId + 1);
        }
        students.add(student);
        return student;
    }

    // PUT - Update an existing student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getId() != null && s.getId().equals(id)) {
                updatedStudent.setId(id);
                students.set(i, updatedStudent);
                return updatedStudent;
            }
        }
        return null;
    }

    // DELETE - Delete student by ID
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        boolean removed = students.removeIf(s -> s.getId() != null && s.getId().equals(id));
        return removed ? "Student removed successfully" : "Student not found";
    }
}
