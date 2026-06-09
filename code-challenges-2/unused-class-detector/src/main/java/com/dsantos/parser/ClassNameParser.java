package com.dsantos.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassNameParser {

    private static final Pattern PATTERN = Pattern.compile(
            "\\b(?:class|interface|enum|record)\\s+(\\w+)",
            Pattern.MULTILINE
    );

    public List<String> parse(Path file) throws IOException {
        String content = Files.readString(file);
        List<String> names = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(content);
        while (matcher.find()) {
            names.add(matcher.group(1));
        }
        return names;
    }
}

