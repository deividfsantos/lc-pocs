package com.dsantos.report;

import com.dsantos.model.DetectionResult;

public class ReportPrinter {

    public void print(DetectionResult result) {
        System.out.println("=== Unused Class Detector ===");
        System.out.println("Total classes: " + result.getAllClasses().size());
        System.out.println("Unused classes: " + result.getUnusedClasses().size());
        System.out.println();
        if (result.getUnusedClasses().isEmpty()) {
            System.out.println("No unused classes found.");
            return;
        }
        System.out.println("Unused:");
        for (String cls : result.getUnusedClasses()) {
            System.out.println("  - " + cls);
        }
    }
}

