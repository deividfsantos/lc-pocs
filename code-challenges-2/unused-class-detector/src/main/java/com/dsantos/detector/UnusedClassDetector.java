package com.dsantos.detector;

import com.dsantos.model.DetectionResult;
import com.dsantos.parser.ClassNameParser;
import com.dsantos.scanner.FileScanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UnusedClassDetector {

    private final FileScanner fileScanner;
    private final ClassNameParser parser;
    private final ReferenceScanner referenceScanner;

    public UnusedClassDetector() {
        this.fileScanner = new FileScanner();
        this.parser = new ClassNameParser();
        this.referenceScanner = new ReferenceScanner();
    }

    public DetectionResult detect(Path directory) throws IOException {
        List<Path> files = fileScanner.scan(directory);

        List<String> allClasses = new ArrayList<>();
        for (Path file : files) {
            allClasses.addAll(parser.parse(file));
        }

        List<String> unusedClasses = new ArrayList<>();
        for (String cls : allClasses) {
            int count = referenceScanner.countReferences(cls, files);
            if (count <= 1) {
                unusedClasses.add(cls);
            }
        }

        return new DetectionResult(allClasses, unusedClasses);
    }
}

