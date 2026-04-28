package com.dsantos.validation;

import com.dsantos.validation.constraints.NotBlank;
import com.dsantos.validation.constraints.NotNull;
import com.dsantos.validation.core.ValidationResult;
import com.dsantos.validation.engine.ValidationEngine;

public class Main {

    public static void main(String[] args) {
        ValidationEngine engine = new ValidationEngine();

        User alice = new User("Alice", "alice@example.com");
        User invalid = new User(null, "");

        System.out.println("Valid user: " + engine.validate(alice));
        System.out.println("Invalid user: " + engine.validate(invalid));
    }

    static class User {
        @NotNull(message = "name must not be null")
        String name;

        @NotBlank(message = "email must not be blank")
        String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
