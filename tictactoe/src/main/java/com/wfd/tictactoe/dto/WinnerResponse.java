package com.wfd.tictactoe.dto;

import com.wfd.tictactoe.model.FinalStatus;

public class WinnerResponse {
    private FinalStatus finalStatus;
    private Integer playerWinner;

    public WinnerResponse(FinalStatus finalStatus, Integer playerWinner) {
        this.finalStatus = finalStatus;
        this.playerWinner = playerWinner;
    }

    public FinalStatus getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(FinalStatus finalStatus) {
        this.finalStatus = finalStatus;
    }

    public Integer getPlayerWinner() {
        return playerWinner;
    }

    public void setPlayerWinner(Integer playerWinner) {
        this.playerWinner = playerWinner;
    }
}
