package com.mystery;

import com.mystery.data.MysteryData;
import com.mystery.model.Clue;
import com.mystery.model.GameResult;
import com.mystery.model.GameState;
import com.mystery.model.Suspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameEngine {
    private static final int CLUES_NEEDED = 3;

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

    public String examine(String location) {
        if (location.isBlank()) {
            return "Specify a location. Type 'locations' to see available locations.";
        }
        List<Clue> found = clues.stream()
            .filter(c -> c.location().equals(location) && !c.found())
            .toList();

        if (found.isEmpty()) {
            return "You search the " + location + " carefully but find nothing new.";
        }

        StringBuilder result = new StringBuilder("You examine the " + location + ":\n");
        for (Clue clue : found) {
            markClueFound(clue.id());
            state.addFoundClue(clue.id());
            result.append("  [CLUE] ").append(clue.description()).append("\n");
        }
        return result.toString().trim();
    }

    public String talk(String suspectName) {
        if (suspectName.isBlank()) {
            return "Specify who you want to talk to. Type 'suspects' to see persons of interest.";
        }
        Optional<Suspect> match = suspects.stream()
            .filter(s -> s.name().toLowerCase().contains(suspectName.toLowerCase()))
            .findFirst();

        if (match.isEmpty()) {
            return "There is no one here by that name.";
        }

        Suspect suspect = match.get();
        state.addInterviewedSuspect(suspect.name());
        return suspect.name() + " (" + suspect.description() + ")\n  \"" + suspect.alibi() + ".\"";
    }

    public GameResult accuse(String name) {
        if (name.isBlank()) {
            return new GameResult.Insufficient(state.getFoundClues().size(), CLUES_NEEDED);
        }
        if (state.getFoundClues().size() < CLUES_NEEDED) {
            return new GameResult.Insufficient(state.getFoundClues().size(), CLUES_NEEDED);
        }
        Optional<Suspect> match = suspects.stream()
            .filter(s -> s.name().toLowerCase().contains(name.toLowerCase()))
            .findFirst();

        if (match.isEmpty()) {
            return new GameResult.Wrong(name, getGuiltyName());
        }
        if (match.get().guilty()) {
            state.setSolved(true);
            return new GameResult.Correct(match.get().name());
        }
        return new GameResult.Wrong(match.get().name(), getGuiltyName());
    }

    public String listSuspects() {
        StringBuilder sb = new StringBuilder("Known persons of interest:\n");
        for (Suspect s : suspects) {
            sb.append("  - ").append(s.name()).append(": ").append(s.description()).append("\n");
        }
        return sb.toString().trim();
    }

    public String listLocations() {
        return """
            Locations you can examine:
              - reception
              - suite
              - security_room
              - bar
              - hallway
              - laundry
              - lobby""";
    }

    public String listFoundClues() {
        if (state.getFoundClues().isEmpty()) {
            return "You have not found any clues yet.";
        }
        StringBuilder sb = new StringBuilder("Clues found so far:\n");
        for (String id : state.getFoundClues()) {
            clues.stream().filter(c -> c.id().equals(id)).findFirst()
                .ifPresent(c -> sb.append("  [").append(c.id()).append("] ").append(c.description()).append("\n"));
        }
        return sb.toString().trim();
    }

    private String getGuiltyName() {
        return suspects.stream().filter(Suspect::guilty).findFirst()
            .map(Suspect::name).orElse("Unknown");
    }

    private void markClueFound(String id) {
        for (int i = 0; i < clues.size(); i++) {
            if (clues.get(i).id().equals(id)) {
                clues.set(i, clues.get(i).withFound());
            }
        }
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
