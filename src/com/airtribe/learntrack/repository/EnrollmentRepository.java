
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        Objects.requireNonNull(
                enrollment,
                "Enrollment cannot be null."
        );

        String id = enrollment.getId();

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Enrollment ID cannot be empty."
            );
        }

        if (existsById(id)) {
            throw new IllegalArgumentException(
                    "Enrollment already exists with ID: " + id
            );
        }

        enrollments.add(enrollment);
    }

    public Enrollment findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Enrollment ID cannot be empty."
            );
        }

        String normalizedId = id.trim();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(normalizedId)) {
                return enrollment;
            }
        }

        throw new NoSuchElementException(
                "Enrollment with ID " + normalizedId + " not found."
        );
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String normalizedId = id.trim();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(normalizedId)) {
                return true;
            }
        }

        return false;
    }

    public boolean existsByStudentIdAndCourseId(
            String studentId,
            String courseId
    ) {
        if (studentId == null || studentId.trim().isEmpty()
                || courseId == null || courseId.trim().isEmpty()) {
            return false;
        }

        String normalizedStudentId = studentId.trim();
        String normalizedCourseId = courseId.trim();

        for (Enrollment enrollment : enrollments) {
            if (Objects.equals(
                    enrollment.getStudentId(),
                    normalizedStudentId
            )
                    && Objects.equals(
                    enrollment.getCourseId(),
                    normalizedCourseId
            )
                    && enrollment.getEnrollmentStatus()
                    == EnrollmentStatus.ENROLLED) {
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

        return enrollments.removeIf(
                enrollment -> enrollment.getId().equals(normalizedId)
        );
    }
}
