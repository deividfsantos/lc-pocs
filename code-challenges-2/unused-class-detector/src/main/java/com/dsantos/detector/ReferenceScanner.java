package com.dsantos.detector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReferenceScanner {

    public int countReferences(String className, List<Path> files) throws IOException {
        int total = 0;
        for (Path file : files) {
            String content = Files.readString(file);
            int index = 0;
            while ((index = content.indexOf(className, index)) != -1) {
                boolean validBefore = index == 0 || !Character.isLetterOrDigit(content.charAt(index - 1));
                boolean validAfter = (index + className.length()) >= content.length()
                        || !Character.isLetterOrDigit(content.charAt(index + className.length()));
                if (validBefore && validAfter) {
                    total++;
                }
                index++;
            }
        }
        return total;
    }
}

