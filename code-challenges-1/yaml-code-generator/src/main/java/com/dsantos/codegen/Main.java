package com.dsantos.codegen;

import com.dsantos.codegen.generator.CodeGenerator;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.parser.YamlParser;

import java.io.File;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: codegen <yaml-file> <output-dir>");
            System.exit(1);
        }

        File yamlFile = new File(args[0]);
        Path outputDir = Path.of(args[1]);

        YamlParser parser = new YamlParser();
        ProjectDefinition project = parser.parse(yamlFile);

        CodeGenerator generator = new CodeGenerator(outputDir);
        generator.generate(project);

        System.out.println("Code generation completed for project: " + project.name());
    }
}

