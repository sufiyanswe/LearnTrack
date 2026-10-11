
package com.airtribe.learntrack.entity;

public class Student extends Person {

    private String batch;
    private boolean active;

    public Student(
            String id,
            String firstName,
            String lastName,
            String email,
            String batch,
            boolean active
    ) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }

    // Overloaded constructor without an email address.
    public Student(
            String id,
            String firstName,
            String lastName,
            String batch,
            boolean active
    ) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = active;
    }

    public String getBatch() {
        return batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getDisplayName() {
        return "Student: " + getFirstName() + " " + getLastName();
    }
}
