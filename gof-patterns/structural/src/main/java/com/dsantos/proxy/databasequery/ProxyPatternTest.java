package com.dsantos.proxy.databasequery;

public class ProxyPatternTest {

    public static void main(String[] args) {
        DatabaseService dbService = new DatabaseServiceProxy("user", "user123");
        try {
            dbService.connect("jdbc:mysql://localhost:3306/mydb");
            dbService.executeQuery("SELECT * FROM users");
        } catch (Exception e) {
            System.out.println("Exception Message::" + e.getMessage());
        }

        DatabaseService adminService = new DatabaseServiceProxy("admin", "admin123");
        try {
            adminService.connect("jdbc:mysql://localhost:3306/mydb");
            adminService.executeQuery("SELECT * FROM users");
        } catch (Exception e) {
            System.out.println("Exception Message::" + e.getMessage());
        }
    }
}