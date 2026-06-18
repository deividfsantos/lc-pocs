package com.mystery.model;

public sealed interface GameResult permits GameResult.Correct, GameResult.Wrong, GameResult.Insufficient {
    record Correct(String suspectName) implements GameResult {}
    record Wrong(String suspectName, String realKiller) implements GameResult {}
    record Insufficient(int cluesFound, int cluesNeeded) implements GameResult {}
}
