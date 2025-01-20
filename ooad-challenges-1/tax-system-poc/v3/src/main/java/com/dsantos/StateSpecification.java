package com.dsantos;

public class StateSpecification implements Specification<State> {
    private final String stateName;

    public StateSpecification(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public boolean isSatisfiedBy(State state) {
        return state.name().equals(stateName);
    }
}