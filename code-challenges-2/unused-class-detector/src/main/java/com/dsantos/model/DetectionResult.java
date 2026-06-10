package com.dsantos.model;

import java.util.List;

public class DetectionResult {

    private final List<String> allClasses;
    private final List<String> unusedClasses;

    public DetectionResult(List<String> allClasses, List<String> unusedClasses) {
        this.allClasses = allClasses;
        this.unusedClasses = unusedClasses;
    }

    public List<String> getAllClasses() {
        return allClasses;
    }

    public List<String> getUnusedClasses() {
        return unusedClasses;
    }
}

