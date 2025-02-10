package com.wfd.tictactoe.exception;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LogManager.getLogger("exceptionHandlerLog");

    @Autowired
    Gson gson;

    @ExceptionHandler(PlayerUnknownException.class)
    public ResponseEntity<ErrorResponse> handlePlayerUnknownException(PlayerUnknownException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMoveException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMoveException(InvalidMoveException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPlayerMoveException.class)
    public ResponseEntity<ErrorResponse> handleWrongPlayerMoveException(WrongPlayerMoveException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBoardException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBoardException(InvalidBoardException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralDeclineException.class)
    public ResponseEntity<ErrorResponse> handleGeneralDeclineException(GeneralDeclineException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<ErrorResponse> handleGeneralDeclineException(GameOverException ex) {
        // Create the error response
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());

        logger.info("[Exception] Response :" + gson.toJson(errorResponse));
        // Return the error response with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
