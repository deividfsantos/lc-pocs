package com.dsantos;

public class YearSpecification implements Specification<Year> {
    private final Integer year;

    public YearSpecification(Integer year) {
        this.year = year;
    }

    @Override
    public boolean isSatisfiedBy(Year year) {
        return year.year().equals(this.year);
    }
}