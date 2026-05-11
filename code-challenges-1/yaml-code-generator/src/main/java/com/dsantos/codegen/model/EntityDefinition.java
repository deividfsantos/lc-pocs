package com.dsantos.codegen.model;

import java.util.List;

public record EntityDefinition(String name, List<FieldDefinition> fields, boolean generateRepository, boolean generateService) {

    public String repositoryName() {
        return name + "Repository";
    }

    public String serviceName() {
        return name + "Service";
    }
}

