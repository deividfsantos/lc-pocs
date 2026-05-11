package com.dsantos.codegen.model;

public enum FieldType {
    STRING,
    INTEGER,
    LONG,
    DOUBLE,
    BOOLEAN,
    LOCAL_DATE,
    LOCAL_DATE_TIME,
    UUID;

    public String toJavaType() {
        return switch (this) {
            case STRING -> "String";
            case INTEGER -> "Integer";
            case LONG -> "Long";
            case DOUBLE -> "Double";
            case BOOLEAN -> "Boolean";
            case LOCAL_DATE -> "LocalDate";
            case LOCAL_DATE_TIME -> "LocalDateTime";
            case UUID -> "UUID";
        };
    }

    public static FieldType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "STRING" -> STRING;
            case "INTEGER", "INT" -> INTEGER;
            case "LONG" -> LONG;
            case "DOUBLE", "FLOAT" -> DOUBLE;
            case "BOOLEAN", "BOOL" -> BOOLEAN;
            case "DATE", "LOCAL_DATE" -> LOCAL_DATE;
            case "DATETIME", "LOCAL_DATE_TIME" -> LOCAL_DATE_TIME;
            case "UUID" -> UUID;
            default -> throw new IllegalArgumentException("Unknown field type: " + value);
        };
    }
}

