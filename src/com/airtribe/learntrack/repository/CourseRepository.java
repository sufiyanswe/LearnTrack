
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CourseRepository {

    private final List<Course> courses = new ArrayList<>();

    public void save(Course course) {
        Objects.requireNonNull(course, "Course cannot be null.");

        String id = course.getId();

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Course ID cannot be empty."
            );
        }

        if (existsById(id)) {
            throw new IllegalArgumentException(
                    "Course already exists with ID: " + id
            );
        }

        courses.add(course);
    }

    public Course findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Course ID cannot be empty."
            );
        }

        String normalizedId = id.trim();

        for (Course course : courses) {
            if (course.getId().equals(normalizedId)) {
                return course;
            }
        }

        throw new NoSuchElementException(
                "Course with ID " + normalizedId + " not found."
        );
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String normalizedId = id.trim();

        for (Course course : courses) {
            if (course.getId().equals(normalizedId)) {
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

        return courses.removeIf(
                course -> course.getId().equals(normalizedId)
        );
    }
}
