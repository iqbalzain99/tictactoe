package com.wfd.tictactoe.util;

import com.wfd.tictactoe.model.Board;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameUtil {

    // Extract certain column into List of Integer
    public static List<List<Integer>> getDiagonals(Board board, int row, int col) {
        List<Integer> mainDiagonal = new ArrayList<>();
        List<Integer> antiDiagonal = new ArrayList<>();

        int size = board.getValue().length;

        // Get main diagonal ↘ (top-left to bottom-right)
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            mainDiagonal.add(0, board.getValue()[i][j]); // Insert at the beginning to maintain order
        }
        for (int i = row + 1, j = col + 1; i < size && j < size; i++, j++) {
            mainDiagonal.add(board.getValue()[i][j]);
        }

        // Get anti-diagonal ↙ (top-right to bottom-left)
        for (int i = row, j = col; i >= 0 && j < size; i--, j++) {
            antiDiagonal.add(0, board.getValue()[i][j]); // Insert at the beginning to maintain order
        }
        for (int i = row + 1, j = col - 1; i < size && j >= 0; i++, j--) {
            antiDiagonal.add(board.getValue()[i][j]);
        }

        // Return both diagonals
        List<List<Integer>> result = new ArrayList<>();
        result.add(antiDiagonal);
        result.add(mainDiagonal);
        return result;
    }

    // Extract certain column into List of Integer
    public static Integer[] getCertainColumn(Board board, Integer colNum) {
        Integer size = board.getValue()[0].length;
        Integer[] line = new Integer[size];

        for (int i = 0; i < size; i++) {
            line[i] = board.getValue()[i][colNum];
        }
        return line;
    }

    // Checking the winning condition for straight line
    public static Integer checkConsecutive(Integer[] line, Integer winCondition) {
        Integer count1 = 0, count2 = 0;

        if (line.length >= winCondition){
            for (Integer cell : line) {
                if (cell == 1) {
                    count1++;
                    count2 = 0; // Reset 2 count
                } else if (cell == 2) {
                    count2++;
                    count1 = 0; // Reset 1 count
                } else {
                    count1 = 0;
                    count2 = 0;
                }

                // Return the winner of the game
                if (count1.equals(winCondition)) {
                    return 1;
                } else if (count2.equals(winCondition)) {
                    return 2;
                }
            }
        }
        // 0 means no winner
        return 0;
    }

//    public static Integer checkConsecutive(Integer[] line, Integer winCondition) {
//        Integer[] players = new Integer[]{1, 2}; // To make the function handle multiplayer mode
////        Integer count1 = 0, count2 = 0;
//        Integer[] playerScoreCount = new Integer[players.length];
//        // Set all values to be 0
//        for (int i = 0; i < players.length; i++) {
//            playerScoreCount[i] = 0;
//        }
//
//        if (line.length >= winCondition){
//            for (Integer cell : line) {
//                for (int i = 0; i < players.length; i++) {
//                    if (cell == i + 1){
//                        playerScoreCount[i] = playerScoreCount[i] + 1;
//                        // Reset counter for other player
//                        for (int j = 0; j < players.length; j++) {
//                            if (j != i) playerScoreCount[j] = 0;
//                        }
//                    }
//                    if (playerScoreCount[i].equals(winCondition)) {
//                        return i + 1;
//                    }
//                }
//            }
//        }
//        // 0 means no winner
//        return 0;
//    }
}
