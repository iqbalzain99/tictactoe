package com.wfd.tictactoe.exception;

public class InvalidBoardException extends Exception{

    private String errorCode = "BOARD-01";

    public InvalidBoardException() {
        super("Please create valid board data!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
