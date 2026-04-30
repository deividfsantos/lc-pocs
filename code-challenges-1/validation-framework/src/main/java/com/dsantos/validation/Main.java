package com.dsantos.validation;

import com.dsantos.validation.constraints.Email;
import com.dsantos.validation.constraints.NotBlank;
import com.dsantos.validation.constraints.NotNull;
import com.dsantos.validation.constraints.Valid;
import com.dsantos.validation.core.ValidationResult;
import com.dsantos.validation.engine.ValidationEngine;

public class Main {

    public static void main(String[] args) {
        ValidationEngine engine = new ValidationEngine();

        Address validAddr = new Address("Maple St", "Springfield");
        User alice = new User("Alice", "alice@example.com", validAddr);

        Address badAddr = new Address("", null);
        User invalid = new User(null, "not-an-email", badAddr);

        System.out.println("Valid user: " + engine.validate(alice));
        System.out.println("Invalid user: " + engine.validate(invalid));
    }

    static class User {
        @NotNull(message = "name must not be null")
        String name;

        @NotBlank
        @Email
        String email;

        @Valid
        Address address;

        User(String name, String email, Address address) {
            this.name = name;
            this.email = email;
            this.address = address;
        }
    }

    static class Address {
        @NotBlank
        String street;

        @NotNull
        String city;

        Address(String street, String city) {
            this.street = street;
            this.city = city;
        }
    }
}
