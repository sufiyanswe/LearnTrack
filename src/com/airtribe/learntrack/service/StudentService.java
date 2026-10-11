
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = Objects.requireNonNull(
                studentRepository,
                "StudentRepository cannot be null."
        );
    }

    public void addStudent(Student student) {
        validateStudent(student);
        validateStudentFields(student);

        normalizeStudentFields(student);

        if (studentRepository.existsById(student.getId())) {
            throw new InvalidInputException(
                    "Student already exists with id: " + student.getId()
            );
        }

        studentRepository.save(student);
    }

    public Student findStudentById(String id) {
        String normalizedId = normalizeAndValidateId(id);

        try {
            return studentRepository.findById(normalizedId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Student not found with id: " + normalizedId
            );
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void updateStudent(Student student) {
        validateStudent(student);
        validateStudentFields(student);

        String normalizedId = normalizeAndValidateId(student.getId());

        Student existingStudent = findStudentById(normalizedId);

        existingStudent.setFirstName(student.getFirstName().trim());
        existingStudent.setLastName(student.getLastName().trim());
        existingStudent.setEmail(student.getEmail().trim());
        existingStudent.setBatch(student.getBatch().trim());
    }

    public void deactivateStudent(String id) {
        Student student = findStudentById(id);
        student.setActive(false);
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new InvalidInputException("Student cannot be null.");
        }
    }

    private void validateStudentFields(Student student) {
        validateRequiredField(student.getId(), "Student ID");
        validateRequiredField(student.getFirstName(), "First name");
        validateRequiredField(student.getLastName(), "Last name");
        validateRequiredField(student.getEmail(), "Email");
        validateRequiredField(student.getBatch(), "Batch");

        validateEmail(student.getEmail().trim());
    }


    private void validateEmail(String email) {
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');
        int lastDotIndex = email.lastIndexOf('.');

        boolean invalid =
                atIndex <= 0
                        || atIndex != lastAtIndex
                        || lastDotIndex <= atIndex + 1
                        || lastDotIndex >= email.length() - 1
                        || containsWhitespace(email)
                        || email.contains("..")
                        || email.startsWith(".")
                        || email.endsWith(".");

        if (invalid) {
            throw new InvalidInputException(
                    "Please provide a valid email address."
            );
        }
    }


    private boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    private void normalizeStudentFields(Student student) {
        // The ID is immutable and must be initialized by the constructor.
        student.setFirstName(student.getFirstName().trim());
        student.setLastName(student.getLastName().trim());
        student.setEmail(student.getEmail().trim());
        student.setBatch(student.getBatch().trim());
    }

    private String normalizeAndValidateId(String id) {
        validateRequiredField(id, "Student ID");
        return id.trim();
    }

    private void validateRequiredField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(
                    fieldName + " cannot be empty."
            );
        }
    }
}
