package com.dsantos.codegen.model;

import java.util.List;

public record ProjectDefinition(String name, String basePackage, List<EntityDefinition> entities) {

    public String basePackagePath() {
        return basePackage.replace('.', '/');
    }
}

