
package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.enums.EnrollmentStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {

    private final String id;
    private final String studentId;
    private final String courseId;
    private final LocalDate enrollmentDate;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(
            String id,
            String studentId,
            String courseId,
            LocalDate enrollmentDate,
            EnrollmentStatus enrollmentStatus
    ) {
        this.id = Objects.requireNonNull(id, "Enrollment ID cannot be null.");
        this.studentId = Objects.requireNonNull(
                studentId, "Student ID cannot be null."
        );
        this.courseId = Objects.requireNonNull(
                courseId, "Course ID cannot be null."
        );
        this.enrollmentDate = Objects.requireNonNull(
                enrollmentDate, "Enrollment date cannot be null."
        );
        this.enrollmentStatus = Objects.requireNonNull(
                enrollmentStatus, "Enrollment status cannot be null."
        );
    }

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = Objects.requireNonNull(
                enrollmentStatus, "Enrollment status cannot be null."
        );
    }
}
