package com.dsantos.facade;

import java.sql.Connection;

public class OracleHelper {

    public static Connection getOracleDBConnection() {
        return null;
    }

    public String generateOraclePDFReport(String tableName, Connection con) {
        return "Oracle PDF report";
    }

    public String generateOracleHTMLReport(String tableName, Connection con) {
        return "Oracle HTML report";
    }

}