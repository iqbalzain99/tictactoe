package com.wfd.tictactoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wfd.tictactoe.dto.GeneralResponse;
import com.wfd.tictactoe.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class TicTacToe {

    private static Logger logger = LogManager.getLogger("ticTacToeModelLog");

    // Win count for both player
    private Integer player1WinCount = 0;
    private Integer player2WinCount = 0;

    // Size of the customisable board
    private Integer boardSize = 0;

    // The condition that state :
    // Player with [[winningCondition]] consecutive mark won the game (diagonally, vertically, or horizontally)
    private Integer winningCondition = 0;

    // Which player should move in this attempt
    private Integer currentMove = 1;

    // 0 = No one wins yet
    // 1 = Player 1 win
    // 2 = Player 2 win
    private Integer winner = 0;

    // Boolean expression when the game reach it's end (got winner / tie)
    private boolean isFinal = false;

    // Board of the game, contains the occupied field by the number of the player.
    private Board board = new Board(new Integer[boardSize][boardSize]);


    // Getter and Setter
    public Integer getPlayer1WinCount() {
        return player1WinCount;
    }

    public void setPlayer1WinCount(Integer player1WinCount) {
        this.player1WinCount = player1WinCount;
    }

    public Integer getPlayer2WinCount() {
        return player2WinCount;
    }

    public void setPlayer2WinCount(Integer player2WinCount) {
        this.player2WinCount = player2WinCount;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardSize(Integer boardSize) throws InvalidBoardException {
        if (boardSize < 0) throw new InvalidBoardException();
        this.boardSize = boardSize;
    }

    public Integer getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(Integer currentMove) {
        this.currentMove = currentMove;
    }

    // Another function goes here
    public void playerMove(Integer playerNumber, Integer col, Integer row) {
        getBoard().setCertainColumn(col, row, playerNumber);
    }

    public void switchMove() {
        if (getCurrentMove() == 1) {
            setCurrentMove(2);
        } else {
            setCurrentMove(1);
        }
    }

    @JsonIgnore
    public String getBoardState() {
        Integer n = boardSize;
        StringBuilder result = new StringBuilder();
        result.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(board.getValue()[i][j]).append(" ");
            }
            result.append("\n"); // Move to the next line after each row
        }
        return result.toString();
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public GeneralResponse convertToActionResponse() {
        return new GeneralResponse(getCurrentMove(), getPlayer1WinCount(), getPlayer2WinCount(), isFinal(), getBoard());
    }


    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public Integer getWinningCondition() {
        return winningCondition;
    }

    public void setWinningCondition(Integer winningCondition) {
        this.winningCondition = winningCondition;
    }
}
