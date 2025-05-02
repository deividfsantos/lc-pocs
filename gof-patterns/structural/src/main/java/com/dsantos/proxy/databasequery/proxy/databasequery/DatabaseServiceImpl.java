package com.dsantos.proxy.databasequery.proxy.databasequery;

public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public void connect(String dbUrl) {
        System.out.println("Connected to database: " + dbUrl);
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("Executing query: " + query);
    }
}