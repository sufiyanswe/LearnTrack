
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = Objects.requireNonNull(
                courseRepository,
                "CourseRepository cannot be null."
        );
    }

    public void addCourse(Course course) {
        validateCourse(course);
        validateCourseFields(course);
        normalizeCourseFields(course);

        if (courseRepository.existsById(course.getId())) {
            throw new InvalidInputException(
                    "Course already exists with id: " + course.getId()
            );
        }

        courseRepository.save(course);
    }

    public Course findCourseById(String id) {
        String normalizedId = normalizeAndValidateId(id);

        try {
            return courseRepository.findById(normalizedId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(
                    "Course not found with id: " + normalizedId
            );
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void updateCourse(Course course) {
        validateCourse(course);
        validateCourseFields(course);

        String normalizedId = normalizeAndValidateId(course.getId());

        Course existingCourse = findCourseById(normalizedId);

        existingCourse.setCourseName(course.getCourseName().trim());
        existingCourse.setDescription(course.getDescription().trim());
        existingCourse.setDurationInWeeks(course.getDurationInWeeks());
    }

    public void activateCourse(String id) {
        Course course = findCourseById(id);
        course.setActive(true);
    }

    public void deactivateCourse(String id) {
        Course course = findCourseById(id);
        course.setActive(false);
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new InvalidInputException("Course cannot be null.");
        }
    }

    private void validateCourseFields(Course course) {
        validateRequiredField(course.getId(), "Course ID");
        validateRequiredField(course.getCourseName(), "Course name");
        validateRequiredField(course.getDescription(), "Description");

        if (course.getDurationInWeeks() <= 0) {
            throw new InvalidInputException(
                    "Course duration must be greater than zero."
            );
        }
    }

    private void normalizeCourseFields(Course course) {
        // Course ID is immutable; normalize only mutable fields.
        course.setCourseName(course.getCourseName().trim());
        course.setDescription(course.getDescription().trim());
    }

    private String normalizeAndValidateId(String id) {
        validateRequiredField(id, "Course ID");
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
