package com.dsantos.codegen.model;

public record FieldDefinition(String name, FieldType type, boolean required, String defaultValue) {

    public FieldDefinition(String name, FieldType type) {
        this(name, type, false, null);
    }

    public String capitalizedName() {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}

