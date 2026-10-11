
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository
    ) {
        this.enrollmentRepository = Objects.requireNonNull(
                enrollmentRepository,
                "EnrollmentRepository cannot be null."
        );
        this.courseRepository = Objects.requireNonNull(
                courseRepository,
                "CourseRepository cannot be null."
        );
        this.studentRepository = Objects.requireNonNull(
                studentRepository,
                "StudentRepository cannot be null."
        );
    }

    public void enrollStudent(String studentId, String courseId) {
        String normalizedStudentId = normalizeAndValidateId(
                studentId, "Student"
        );
        String normalizedCourseId = normalizeAndValidateId(
                courseId, "Course"
        );

        Student student;
        try {
            student = studentRepository.findById(normalizedStudentId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Student not found with ID: " + normalizedStudentId
            );
        }

        Course course;
        try {
            course = courseRepository.findById(normalizedCourseId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Course not found with ID: " + normalizedCourseId
            );
        }

        if (!student.isActive()) {
            throw new InvalidInputException(
                    "Cannot enroll an inactive student."
            );
        }

        if (!course.isActive()) {
            throw new InvalidInputException(
                    "Cannot enroll in an inactive course."
            );
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(
                normalizedStudentId, normalizedCourseId)) {
            throw new InvalidInputException(
                    "Student already has an active enrollment in this course."
            );
        }

        Enrollment enrollment = new Enrollment(
                IdGenerator.generateEnrollmentId(),
                normalizedStudentId,
                normalizedCourseId,
                LocalDate.now(),
                EnrollmentStatus.ENROLLED
        );

        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        String normalizedStudentId = normalizeAndValidateId(
                studentId, "Student"
        );

        try {
            studentRepository.findById(normalizedStudentId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Student not found with ID: " + normalizedStudentId
            );
        }

        List<Enrollment> studentEnrollments = new ArrayList<>();

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (normalizedStudentId.equals(enrollment.getStudentId())) {
                studentEnrollments.add(enrollment);
            }
        }

        return studentEnrollments;
    }

    public void updateEnrollmentStatus(
            String enrollmentId,
            EnrollmentStatus status
    ) {
        String normalizedEnrollmentId = normalizeAndValidateId(
                enrollmentId, "Enrollment"
        );

        if (status == null) {
            throw new InvalidInputException(
                    "Enrollment status cannot be null."
            );
        }

        if (status == EnrollmentStatus.ENROLLED) {
            throw new InvalidInputException(
                    "An enrollment status cannot be reset to ENROLLED."
            );
        }

        Enrollment enrollment;
        try {
            enrollment = enrollmentRepository.findById(
                    normalizedEnrollmentId
            );
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Enrollment not found with ID: "
                            + normalizedEnrollmentId
            );
        }

        if (enrollment.getEnrollmentStatus() != EnrollmentStatus.ENROLLED) {
            throw new InvalidInputException(
                    "Only ENROLLED records can be completed or cancelled."
            );
        }

        enrollment.setEnrollmentStatus(status);
    }

    private String normalizeAndValidateId(String id, String entityName) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidInputException(
                    entityName + " ID cannot be empty."
            );
        }

        return id.trim();
    }
}
