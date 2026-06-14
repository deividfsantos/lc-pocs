package com.mystery.model;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private final Set<String> foundClues = new HashSet<>();
    private final Set<String> interviewedSuspects = new HashSet<>();
    private boolean solved = false;

    public void addFoundClue(String clueId) {
        foundClues.add(clueId);
    }

    public void addInterviewedSuspect(String suspectName) {
        interviewedSuspects.add(suspectName);
    }

    public boolean hasFoundClue(String clueId) {
        return foundClues.contains(clueId);
    }

    public boolean hasSuspectBeenInterviewed(String name) {
        return interviewedSuspects.contains(name);
    }

    public Set<String> getFoundClues() {
        return Set.copyOf(foundClues);
    }

    public Set<String> getInterviewedSuspects() {
        return Set.copyOf(interviewedSuspects);
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }
}
