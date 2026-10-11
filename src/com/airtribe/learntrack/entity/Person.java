
package com.airtribe.learntrack.entity;

public abstract class Person {

    private final String id;
    private String firstName;
    private String lastName;
    private String email;

    public Person(
            String id,
            String firstName,
            String lastName,
            String email
    ) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Person ID cannot be null or empty."
            );
        }

        this.id = id.trim();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Person(
            String id,
            String firstName,
            String lastName
    ) {
        this(id, firstName, lastName, null);
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getDisplayName();
}
