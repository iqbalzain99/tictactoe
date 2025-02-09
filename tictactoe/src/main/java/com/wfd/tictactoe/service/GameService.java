package com.wfd.tictactoe.service;

import com.wfd.tictactoe.dto.GeneralResponse;
import com.wfd.tictactoe.dto.MoveRequest;
import com.wfd.tictactoe.dto.WinnerResponse;
import com.wfd.tictactoe.exception.*;
import com.wfd.tictactoe.model.Board;

public interface GameService {

    // Initializing game, reset all game progress
    GeneralResponse initializeGame(int boardSize) throws InvalidBoardException;

    // Recreate a new round of the game, but keep the score
    GeneralResponse newGame(int boardSize) throws InvalidBoardException;

    // Command to a player to certain coordinates within the board
    GeneralResponse makeMove(MoveRequest moveRequest) throws InvalidMoveException, PlayerUnknownException, WrongPlayerMoveException, InvalidBoardException, GameOverException;

    // Get the winner of the game
    WinnerResponse getWinner();

    // Debug purpose only
    // Return string of current board state
    String getBoard() throws InvalidBoardException;

}
