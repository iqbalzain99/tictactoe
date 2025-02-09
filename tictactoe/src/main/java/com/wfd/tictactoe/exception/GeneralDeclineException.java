package com.wfd.tictactoe.exception;

public class GeneralDeclineException extends Exception{

    private String errorCode = "GENERAL-01";

    public GeneralDeclineException() {
        super("Please provide valid data!");
    }

    public String getErrorCode() {
        return errorCode;
    }
}
