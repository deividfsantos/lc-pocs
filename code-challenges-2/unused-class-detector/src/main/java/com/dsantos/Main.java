package com.dsantos;

import com.dsantos.detector.UnusedClassDetector;
import com.dsantos.model.DetectionResult;
import com.dsantos.report.ReportPrinter;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: unused-class-detector <directory>");
            return;
        }

        Path directory = Path.of(args[0]);
        UnusedClassDetector detector = new UnusedClassDetector();
        DetectionResult result = detector.detect(directory);

        ReportPrinter printer = new ReportPrinter();
        printer.print(result);
    }
}


