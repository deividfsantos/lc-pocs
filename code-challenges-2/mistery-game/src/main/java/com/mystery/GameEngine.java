package com.mystery;

import com.mystery.data.MysteryData;
import com.mystery.model.Clue;
import com.mystery.model.GameState;
import com.mystery.model.Suspect;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final List<Clue> clues;
    private final List<Suspect> suspects;
    private final GameState state;

    public GameEngine() {
        this.clues = new ArrayList<>(MysteryData.clues());
        this.suspects = new ArrayList<>(MysteryData.suspects());
        this.state = new GameState();
    }

    public GameEngine(List<Clue> clues, List<Suspect> suspects) {
        this.clues = new ArrayList<>(clues);
        this.suspects = new ArrayList<>(suspects);
        this.state = new GameState();
    }

    public GameState getState() {
        return state;
    }

    public List<Clue> getClues() {
        return List.copyOf(clues);
    }

    public List<Suspect> getSuspects() {
        return List.copyOf(suspects);
    }
}
