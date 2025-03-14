package com.dsantos.prototype;

public class Manager implements EmployeePrototype {

    private final String name;

    public Manager(String name) {
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