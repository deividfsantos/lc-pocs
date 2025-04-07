package com.dsantos.proxy.databasequery;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseServiceProxyTest {

    @Test
    public void testUnauthorizedAccess() {
        DatabaseSerice dbService = new DatabaseServiceProxy("user", "user123");
        Exception exception = assertThrows(Exception.class, () -> {
            dbService.connect("jdbc:mysql://localhost:3306/mydb");
        });
        assertEquals("Unauthorized access to database.", exception.getMessage());

        exception = assertThrows(Exception.class, () -> {
            dbService.executeQuery("SELECT * FROM users");
        });
        assertEquals("Unauthorized access to execute query.", exception.getMessage());
    }

    @Test
    public void testAuthorizedAccess() {
        DatabaseService dbService = new DatabaseServiceProxy("admin", "admin123");
        assertDoesNotThrow(() -> {
            dbService.connect("jdbc:mysql://localhost:3306/mydb");
        });

        assertDoesNotThrow(() -> {
            dbService.executeQuery("SELECT * FROM users");
        });
    }
}