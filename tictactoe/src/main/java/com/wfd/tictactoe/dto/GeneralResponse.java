package com.wfd.tictactoe.dto;

import com.wfd.tictactoe.model.Board;

public class GeneralResponse {
    // currentMove indicate which player should move next
    private Integer nextMove;

    // Score of each player
    private Integer player1Score;
    private Integer player2Score;

    // Is final shows whether the game is final -> any player win / draw
    private Boolean isFinal;

    // The board of the game
    private Board board;

    public GeneralResponse(Integer nextMove, Integer player1Score, Integer player2Score, Boolean isFinal, Board board) {
        this.nextMove = nextMove;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.isFinal = isFinal;
        this.board = board;
    }

    public Integer getNextMove() {
        return nextMove;
    }

    public void setNextMove(Integer nextMove) {
        this.nextMove = nextMove;
    }

    public Integer getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(Integer player1Score) {
        this.player1Score = player1Score;
    }

    public Integer getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(Integer player2Score) {
        this.player2Score = player2Score;
    }

    public Boolean getAnyWinner() {
        return isFinal;
    }

    public void setAnyWinner(Boolean anyWinner) {
        isFinal = anyWinner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
