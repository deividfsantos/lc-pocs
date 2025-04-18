package com.dsantos.facade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelperFacadeTest {

    @Test
    void testGenerateMySqlHTMLReport() {
        String report = HelperFacade.generateReport(HelperFacade.DBTypes.MYSQL, HelperFacade.ReportTypes.HTML, "test_table");

        assertNotNull(report, "The report should not be null");
        assertEquals("Mysql HTML report", report);
    }

    @Test
    void testGenerateMySqlPDFReport() {
        String report = HelperFacade.generateReport(HelperFacade.DBTypes.MYSQL, HelperFacade.ReportTypes.PDF, "test_table");

        assertNotNull(report, "The report should not be null");
        assertEquals("Mysql PDF report", report);
    }

    @Test
    void testGenerateOracleHTMLReport() {
        String report = HelperFacade.generateReport(HelperFacade.DBTypes.ORACLE, HelperFacade.ReportTypes.HTML, "test_table");

        assertNotNull(report, "The report should not be null");
        assertEquals("Oracle HTML report", report);
    }

    @Test
    void testGenerateOraclePDFReport() {
        String report = HelperFacade.generateReport(HelperFacade.DBTypes.ORACLE, HelperFacade.ReportTypes.PDF, "test_table");

        assertNotNull(report, "The report should not be null");
        assertEquals("Oracle PDF report", report);
    }
}