package com.wfd.tictactoe.exception;

public class PlayerUnknownException extends Exception{

    private String errorCode = "PLAYER-01";
    public PlayerUnknownException() {
        super("Please provide a valid player number!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
