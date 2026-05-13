package com.dsantos.codegen.generator;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.FieldDefinition;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class EntityGenerator {

    private final TemplateEngine templateEngine;

    public EntityGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generate(ProjectDefinition project, EntityDefinition entity, Path outputDir) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("packageName", project.basePackage() + ".model");
        model.put("entity", entity);
        model.put("fields", entity.fields());

        String imports = buildImports(entity);
        model.put("imports", imports);

        String content = templateEngine.render("entity.ftl", model);

        Path packageDir = outputDir.resolve(project.basePackagePath()).resolve("model");
        Files.createDirectories(packageDir);

        Path file = packageDir.resolve(entity.name() + ".java");
        Files.writeString(file, content);

        System.out.println("Generated entity: " + file);
    }

    private String buildImports(EntityDefinition entity) {
        StringBuilder sb = new StringBuilder();
        boolean needsDate = entity.fields().stream()
                .anyMatch(f -> f.type().toJavaType().contains("LocalDate"));
        boolean needsUUID = entity.fields().stream()
                .anyMatch(f -> f.type().toJavaType().equals("UUID"));

        if (needsDate) {
            sb.append("import java.time.LocalDate;\n");
            sb.append("import java.time.LocalDateTime;\n");
        }
        if (needsUUID) {
            sb.append("import java.util.UUID;\n");
        }

        return sb.toString().trim();
    }
}

