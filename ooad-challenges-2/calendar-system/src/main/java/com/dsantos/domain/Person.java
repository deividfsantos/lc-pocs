package com.dsantos.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable value object representing a person who can participate in meetings.
 * Equality is based on email address to prevent ghost duplicates.
 */
public final class Person {

    private final UUID id;
    private final String name;
    private final String email;

    public Person(String name, String email) {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        if (name.isBlank()) throw new IllegalArgumentException("Name must not be blank");
        if (email.isBlank()) throw new IllegalArgumentException("Email must not be blank");

        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person other)) return false;
        return email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email.toLowerCase());
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', email='" + email + "'}";
    }
}

