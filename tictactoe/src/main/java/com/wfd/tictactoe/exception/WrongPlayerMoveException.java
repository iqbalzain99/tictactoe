package com.wfd.tictactoe.exception;

public class WrongPlayerMoveException extends Exception{

    private String errorCode = "PLAYER-02";

    public WrongPlayerMoveException() {
        super("Please select correct player to move!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
