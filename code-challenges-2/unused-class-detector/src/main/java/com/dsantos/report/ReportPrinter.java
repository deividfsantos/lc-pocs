package com.dsantos.report;

import com.dsantos.model.DetectionResult;

public class ReportPrinter {

    public void print(DetectionResult result) {
        int total = result.getAllClasses().size();
        int unused = result.getUnusedClasses().size();
        double pct = total == 0 ? 0.0 : (unused * 100.0 / total);

        System.out.println("=== Unused Class Detector ===");
        System.out.println("Scanned : " + total + " classes");
        System.out.println("Unused  : " + unused + " (" + String.format("%.1f", pct) + "%)");
        System.out.println();
        if (result.getUnusedClasses().isEmpty()) {
            System.out.println("No unused classes detected.");
            return;
        }
        System.out.println("Unused classes:");
        for (String cls : result.getUnusedClasses()) {
            System.out.println("  - " + cls);
        }
        System.out.println();
        System.out.println("Done.");
    }
}


