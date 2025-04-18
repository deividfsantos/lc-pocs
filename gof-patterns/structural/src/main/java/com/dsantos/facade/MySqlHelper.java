package com.dsantos.facade;

import java.sql.Connection;

public class MySqlHelper {

    public static Connection getMySqlDBConnection() {
        return null;
    }

    public String generateMySqlPDFReport(String tableName, Connection con) {
        return "Mysql PDF report";
    }

    public String generateMySqlHTMLReport(String tableName, Connection con) {
        return "Mysql HTML report";
    }
}