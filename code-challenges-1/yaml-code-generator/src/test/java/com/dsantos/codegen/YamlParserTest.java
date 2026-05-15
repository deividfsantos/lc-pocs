package com.dsantos.codegen;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.FieldDefinition;
import com.dsantos.codegen.model.FieldType;
import com.dsantos.codegen.model.ProjectDefinition;
import com.dsantos.codegen.parser.YamlParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class YamlParserTest {

    private final YamlParser parser = new YamlParser();

    @Test
    void parsesProjectDefinition(@TempDir Path tempDir) throws Exception {
        String yaml = """
                name: my-project
                basePackage: com.example
                entities:
                  - name: User
                    generateRepository: true
                    generateService: true
                    fields:
                      - name: id
                        type: UUID
                        required: true
                      - name: username
                        type: STRING
                        required: true
                      - name: age
                        type: INTEGER
                """;

        File file = tempDir.resolve("project.yaml").toFile();
        try (FileWriter w = new FileWriter(file)) {
            w.write(yaml);
        }

        ProjectDefinition project = parser.parse(file);

        assertEquals("my-project", project.name());
        assertEquals("com.example", project.basePackage());
        assertEquals(1, project.entities().size());

        EntityDefinition user = project.entities().getFirst();
        assertEquals("User", user.name());
        assertTrue(user.generateRepository());
        assertEquals(3, user.fields().size());

        FieldDefinition idField = user.fields().getFirst();
        assertEquals("id", idField.name());
        assertEquals(FieldType.UUID, idField.type());
        assertTrue(idField.required());
    }

    @Test
    void parsesMultipleEntities(@TempDir Path tempDir) throws Exception {
        String yaml = """
                name: shop
                basePackage: com.shop
                entities:
                  - name: Product
                    fields:
                      - name: name
                        type: STRING
                  - name: Order
                    fields:
                      - name: total
                        type: DOUBLE
                """;

        File file = tempDir.resolve("shop.yaml").toFile();
        try (FileWriter w = new FileWriter(file)) {
            w.write(yaml);
        }

        ProjectDefinition project = parser.parse(file);
        assertEquals(2, project.entities().size());
    }
}

