package com.dsantos;


public class YearSpecification implements Specification<Year> {
    private final Year year;

    public YearSpecification(Year year) {
        this.year = year;
    }

    @Override
    public boolean isSatisfiedBy(Year year) {
        return this.year.equals(year);
    }
}