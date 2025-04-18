package com.dsantos.facade;

import java.sql.Connection;
public class HelperFacade {


    public static String generateReport(DBTypes dbType, ReportTypes reportType, String tableName) {

        switch (dbType) {
            case MYSQL:
                Connection mySqlDBConnection = MySqlHelper.getMySqlDBConnection();
                MySqlHelper mySqlHelper = new MySqlHelper();
                return switch (reportType) {
                    case HTML -> mySqlHelper.generateMySqlHTMLReport(tableName, mySqlDBConnection);
                    case PDF -> mySqlHelper.generateMySqlPDFReport(tableName, mySqlDBConnection);
                };
            case ORACLE:
                Connection oracleDBConnection = OracleHelper.getOracleDBConnection();
                OracleHelper oracleHelper = new OracleHelper();
                return switch (reportType) {
                    case HTML -> oracleHelper.generateOracleHTMLReport(tableName, oracleDBConnection);
                    case PDF -> oracleHelper.generateOraclePDFReport(tableName, oracleDBConnection);
                };
        }
        return null;
    }

    public static enum DBTypes {
        MYSQL, ORACLE;
    }

    public static enum ReportTypes {
        HTML, PDF;
    }
}