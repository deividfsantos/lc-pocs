package com.dsantos.codegen.generator;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RepositoryGenerator {

    private final TemplateEngine templateEngine;

    public RepositoryGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generate(ProjectDefinition project, EntityDefinition entity, Path outputDir) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("packageName", project.basePackage() + ".repository");
        model.put("modelPackage", project.basePackage() + ".model");
        model.put("entity", entity);

        String content = templateEngine.render("repository.ftl", model);

        Path packageDir = outputDir.resolve(project.basePackagePath()).resolve("repository");
        Files.createDirectories(packageDir);

        Path file = packageDir.resolve(entity.repositoryName() + ".java");
        Files.writeString(file, content);

        System.out.println("Generated repository: " + file);
    }
}

