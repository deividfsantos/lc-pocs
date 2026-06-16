package com.mystery;

import com.mystery.model.Clue;
import com.mystery.model.Suspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    private GameEngine engine;

    @BeforeEach
    void setUp() {
        List<Clue> clues = List.of(
            new Clue("clue1", "A suspicious note on the desk.", "office", false),
            new Clue("clue2", "A broken vase on the floor.", "hallway", false)
        );
        List<Suspect> suspects = List.of(
            new Suspect("John Doe", "The accountant.", "Was at the library all night", false),
            new Suspect("Jane Smith", "The assistant.", "Claims to have been home", true)
        );
        engine = new GameEngine(clues, suspects);
    }

    @Test
    void examineKnownLocationFindsClue() {
        String result = engine.examine("office");
        assertTrue(result.contains("suspicious note"));
        assertTrue(engine.getState().hasFoundClue("clue1"));
    }

    @Test
    void examineUnknownLocationReturnsNothing() {
        String result = engine.examine("kitchen");
        assertTrue(result.contains("nothing new"));
    }

    @Test
    void examineAlreadyFoundClueReturnsNothing() {
        engine.examine("office");
        String second = engine.examine("office");
        assertTrue(second.contains("nothing new"));
    }

    @Test
    void examineBlankLocationReturnsGuidance() {
        String result = engine.examine("");
        assertTrue(result.contains("Specify"));
    }

    @Test
    void talkToKnownSuspectReturnsAlibi() {
        String result = engine.talk("john");
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("library"));
    }

    @Test
    void talkToUnknownSuspectReturnsError() {
        String result = engine.talk("nobody");
        assertTrue(result.contains("no one"));
    }

    @Test
    void talkRecordsSuspectAsInterviewed() {
        engine.talk("jane");
        assertTrue(engine.getState().hasSuspectBeenInterviewed("Jane Smith"));
    }

    @Test
    void talkBlankNameReturnsGuidance() {
        String result = engine.talk("");
        assertTrue(result.contains("Specify"));
    }

    @Test
    void listFoundCluesWhenEmpty() {
        String result = engine.listFoundClues();
        assertTrue(result.contains("not found any clues"));
    }

    @Test
    void listFoundCluesAfterExamine() {
        engine.examine("office");
        String result = engine.listFoundClues();
        assertTrue(result.contains("clue1"));
    }
}
