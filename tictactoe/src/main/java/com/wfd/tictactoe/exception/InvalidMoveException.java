package com.wfd.tictactoe.exception;

public class InvalidMoveException extends Exception{

    private String errorCode = "MOVE-01";
    public InvalidMoveException() {
        super("Please move to the valid cell within the board!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
