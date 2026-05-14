package com.dsantos.codegen.generator;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Path;

public class CodeGenerator {

    private final Path outputDir;
    private final TemplateEngine templateEngine;
    private final EntityGenerator entityGenerator;
    private final RepositoryGenerator repositoryGenerator;
    private final ServiceGenerator serviceGenerator;

    public CodeGenerator(Path outputDir) throws IOException {
        this.outputDir = outputDir;
        this.templateEngine = new TemplateEngine();
        this.entityGenerator = new EntityGenerator(templateEngine);
        this.repositoryGenerator = new RepositoryGenerator(templateEngine);
        this.serviceGenerator = new ServiceGenerator(templateEngine);
    }

    public void generate(ProjectDefinition project) throws IOException {
        for (EntityDefinition entity : project.entities()) {
            entityGenerator.generate(project, entity, outputDir);

            if (entity.generateRepository()) {
                repositoryGenerator.generate(project, entity, outputDir);
            }

            if (entity.generateService()) {
                serviceGenerator.generate(project, entity, outputDir);
            }
        }
    }
}

