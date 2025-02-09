package com.wfd.tictactoe.exception;

public class GameOverException extends Exception{

    private String errorCode = "GAME-01";

    public GameOverException() {
        super("Please start another game!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
