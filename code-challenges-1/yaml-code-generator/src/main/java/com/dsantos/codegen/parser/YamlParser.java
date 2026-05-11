package com.dsantos.codegen.parser;

import com.dsantos.codegen.model.EntityDefinition;
import com.dsantos.codegen.model.FieldDefinition;
import com.dsantos.codegen.model.FieldType;
import com.dsantos.codegen.model.ProjectDefinition;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlParser {

    private final Yaml yaml = new Yaml();

    public ProjectDefinition parse(File file) throws IOException {
        try (var input = new FileInputStream(file)) {
            Map<String, Object> root = yaml.load(input);
            return parseProject(root);
        }
    }

    @SuppressWarnings("unchecked")
    private ProjectDefinition parseProject(Map<String, Object> root) {
        String name = (String) root.get("name");
        String basePackage = (String) root.get("basePackage");
        List<Map<String, Object>> entitiesRaw = (List<Map<String, Object>>) root.get("entities");

        List<EntityDefinition> entities = new ArrayList<>();
        if (entitiesRaw != null) {
            for (Map<String, Object> entityRaw : entitiesRaw) {
                entities.add(parseEntity(entityRaw));
            }
        }

        return new ProjectDefinition(name, basePackage, entities);
    }

    @SuppressWarnings("unchecked")
    private EntityDefinition parseEntity(Map<String, Object> raw) {
        String name = (String) raw.get("name");
        boolean generateRepository = Boolean.TRUE.equals(raw.getOrDefault("generateRepository", true));
        boolean generateService = Boolean.TRUE.equals(raw.getOrDefault("generateService", true));

        List<Map<String, Object>> fieldsRaw = (List<Map<String, Object>>) raw.get("fields");
        List<FieldDefinition> fields = new ArrayList<>();

        if (fieldsRaw != null) {
            for (Map<String, Object> fieldRaw : fieldsRaw) {
                fields.add(parseField(fieldRaw));
            }
        }

        return new EntityDefinition(name, fields, generateRepository, generateService);
    }

    private FieldDefinition parseField(Map<String, Object> raw) {
        String name = (String) raw.get("name");
        FieldType type = FieldType.fromString((String) raw.get("type"));
        boolean required = Boolean.TRUE.equals(raw.getOrDefault("required", false));
        String defaultValue = (String) raw.getOrDefault("default", null);
        return new FieldDefinition(name, type, required, defaultValue);
    }
}

