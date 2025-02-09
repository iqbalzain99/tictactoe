package com.wfd.tictactoe.model;

public class Board {
    private Integer[][] value;

    public Board(Integer[][] value) {
        this.value = value;
    }

    public Integer[][] getValue() {
        return value;
    }

    public void setValue(Integer[][] value) {
        this.value = value;
    }

    public void setCertainColumn(Integer col, Integer row, Integer newValue) {
        this.value[row][col] = newValue;
    }
}
