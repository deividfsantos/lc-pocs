package com.dsantos.codegen;

import com.dsantos.codegen.generator.CodeGenerator;
import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.FieldDefinition;
import com.dsantos.codegen.model.FieldType;
import com.dsantos.codegen.model.ProjectDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void generatesEntityFile(@TempDir Path outputDir) throws Exception {
        var fields = List.of(
                new FieldDefinition("id", FieldType.UUID, true, null),
                new FieldDefinition("name", FieldType.STRING, true, null),
                new FieldDefinition("age", FieldType.INTEGER, false, null)
        );
        var entity = new EntityDefinition("Person", fields, true, true);
        var project = new ProjectDefinition("test-project", "com.example", List.of(entity));

        CodeGenerator generator = new CodeGenerator(outputDir);
        generator.generate(project);

        Path entityFile = outputDir.resolve("com/example/model/Person.java");
        assertTrue(Files.exists(entityFile));
        String content = Files.readString(entityFile);
        assertTrue(content.contains("package com.example.model"));
        assertTrue(content.contains("record Person"));

        Path repoFile = outputDir.resolve("com/example/repository/PersonRepository.java");
        assertTrue(Files.exists(repoFile));

        Path serviceFile = outputDir.resolve("com/example/service/PersonService.java");
        assertTrue(Files.exists(serviceFile));
    }

    @Test
    void skipsRepositoryAndServiceWhenDisabled(@TempDir Path outputDir) throws Exception {
        var fields = List.of(new FieldDefinition("value", FieldType.STRING));
        var entity = new EntityDefinition("Config", fields, false, false);
        var project = new ProjectDefinition("cfg", "com.cfg", List.of(entity));

        CodeGenerator generator = new CodeGenerator(outputDir);
        generator.generate(project);

        Path entityFile = outputDir.resolve("com/cfg/model/Config.java");
        assertTrue(Files.exists(entityFile));

        Path repoFile = outputDir.resolve("com/cfg/repository/ConfigRepository.java");
        assertFalse(Files.exists(repoFile));

        Path serviceFile = outputDir.resolve("com/cfg/service/ConfigService.java");
        assertFalse(Files.exists(serviceFile));
    }
}

