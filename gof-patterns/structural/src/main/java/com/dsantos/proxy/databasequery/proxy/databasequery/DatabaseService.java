package com.dsantos.proxy.databasequery.proxy.databasequery;

public interface DatabaseService {
    void connect(String dbUrl) throws Exception;

    void executeQuery(String query) throws Exception;
}