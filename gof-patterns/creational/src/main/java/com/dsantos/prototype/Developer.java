package com.dsantos.prototype;

public class Developer implements EmployeePrototype {

    private final String name;

    public Developer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public EmployeePrototype clone() {
        return new Developer(name);
    }
}