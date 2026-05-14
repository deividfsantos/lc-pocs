package com.dsantos.codegen.generator;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ServiceGenerator {

    private final TemplateEngine templateEngine;

    public ServiceGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generate(ProjectDefinition project, EntityDefinition entity, Path outputDir) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("packageName", project.basePackage() + ".service");
        model.put("modelPackage", project.basePackage() + ".model");
        model.put("repositoryPackage", project.basePackage() + ".repository");
        model.put("entity", entity);

        String content = templateEngine.render("service.ftl", model);

        Path packageDir = outputDir.resolve(project.basePackagePath()).resolve("service");
        Files.createDirectories(packageDir);

        Path file = packageDir.resolve(entity.serviceName() + ".java");
        Files.writeString(file, content);

        System.out.println("Generated service: " + file);
    }
}

