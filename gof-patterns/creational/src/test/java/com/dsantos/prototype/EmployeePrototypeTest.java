package com.dsantos.prototype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EmployeePrototypeTest {

    @Test
    void testEmployeePrototype() {
        Manager manager = new Manager("Alice");
        Developer developer = new Developer("Bob");

        EmployeePrototype clonedManager = manager.clone();
        EmployeePrototype clonedDeveloper = developer.clone();

        assertNotEquals(manager, clonedManager);
        assertEquals("Alice", clonedManager.name());
        assertNotEquals(developer, clonedDeveloper);
        assertEquals("Bob", clonedDeveloper.name());
    }
}