
package com.airtribe.learntrack.entity;

public class Course {

    private final String id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private boolean active;

    public Course(
            String id,
            String courseName,
            String description,
            int durationInWeeks,
            boolean active
    ) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Course ID cannot be null or empty."
            );
        }

        this.id = id.trim();
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.active = active;
    }

    public Course(
            String id,
            String courseName,
            String description,
            int durationInWeeks
    ) {
        this(id, courseName, description, durationInWeeks, true);
    }

    public String getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public boolean isActive() {
        return active;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
