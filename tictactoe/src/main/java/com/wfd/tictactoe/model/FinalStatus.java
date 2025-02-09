package com.wfd.tictactoe.model;

public enum FinalStatus {

    // enum constants calling the enum constructors
    DRAW("draw"), // Game doesn't have a winner
    WIN("win"), // One of the player win the game
    ONGOING("ongoing"); // One of the player win the game

    private final String value;

    // private enum constructor
    private FinalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
