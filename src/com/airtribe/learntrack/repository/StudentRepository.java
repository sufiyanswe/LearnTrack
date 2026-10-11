
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class StudentRepository {

    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        Objects.requireNonNull(student, "Student cannot be null.");

        String id = student.getId();

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student ID cannot be empty."
            );
        }

        if (existsById(id)) {
            throw new IllegalArgumentException(
                    "Student already exists with ID: " + id
            );
        }

        students.add(student);
    }

    public Student findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student ID cannot be empty."
            );
        }

        String normalizedId = id.trim();

        for (Student student : students) {
            if (student.getId().equals(normalizedId)) {
                return student;
            }
        }

        throw new NoSuchElementException(
                "Student with ID " + normalizedId + " not found."
        );
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String normalizedId = id.trim();

        for (Student student : students) {
            if (student.getId().equals(normalizedId)) {
                return true;
            }
        }

        return false;
    }

    public boolean deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String normalizedId = id.trim();

        return students.removeIf(
                student -> student.getId().equals(normalizedId)
        );
    }
}
