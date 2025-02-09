package com.wfd.tictactoe.service.impl;

import com.wfd.tictactoe.dto.GeneralResponse;
import com.wfd.tictactoe.dto.MoveRequest;
import com.wfd.tictactoe.dto.WinnerResponse;
import com.wfd.tictactoe.exception.*;
import com.wfd.tictactoe.model.Board;
import com.wfd.tictactoe.model.FinalStatus;
import com.wfd.tictactoe.model.TicTacToe;
import com.wfd.tictactoe.service.GameService;
import com.wfd.tictactoe.util.GameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private static Logger logger = LogManager.getLogger("gameServiceLog");

    @Autowired
    TicTacToe ticTacToe;

    @Override
    public GeneralResponse initializeGame(int boardSize) throws InvalidBoardException {
        // Reset all the board and the data
        ticTacToe.setPlayer1WinCount(0);
        ticTacToe.setPlayer2WinCount(0);
        ticTacToe.setWinner(0);
        ticTacToe.setFinal(false);
        // Set the board size data
        setupBoard(boardSize);
        // Fetch all the data that needed for the response and return it as GeneralResponse class
        return ticTacToe.convertToActionResponse();
    }

    @Override
    public GeneralResponse makeMove(MoveRequest moveRequest) throws InvalidMoveException, PlayerUnknownException, WrongPlayerMoveException, InvalidBoardException, GameOverException {
        // Get the value that is needed
        Integer player = moveRequest.getPlayer();
        Integer col = moveRequest.getCol();
        Integer row = moveRequest.getRow();

        // Do all the validation here before the data get moved
        if (ticTacToe.getBoardSize() == 0) throw new InvalidBoardException();
        if (!(player == 1 || player == 2)) throw new PlayerUnknownException();
        if (!player.equals(ticTacToe.getCurrentMove())) throw new WrongPlayerMoveException();
        if (col >= ticTacToe.getBoardSize() || row >= ticTacToe.getBoardSize()) throw new InvalidMoveException();
        if (ticTacToe.getBoard().getValue()[row][col] != 0) throw new InvalidMoveException();
        if (ticTacToe.isFinal()) throw new GameOverException();

        // Move the coordinate of the data
        ticTacToe.playerMove(player, col, row);

        // Switch the movement between player 1 and 2
        ticTacToe.switchMove();

        // Check the winner & set the winner variable if the winner is set
        // Coordinate being sent to check for the current diagonal
        checkForWinner(col, row);

        // Is all the board cell is filled, update isFinal
        if (isBoardFilled(ticTacToe.getBoard())){
            logger.info("isBoardFilled - Finalizing game!");
            ticTacToe.setFinal(true);
        }

        logger.info("Is it final:" + ticTacToe.isFinal());
        logger.info("Winner:" + ticTacToe.getWinner());

        // Return the desired response
        return ticTacToe.convertToActionResponse();
    }

    @Override
    public WinnerResponse getWinner() {
        // Preparing the final status and the winner data
        FinalStatus finalStatus;
        Integer winner = ticTacToe.getWinner();
        // Set the status accordingly
        if (!ticTacToe.isFinal()) {
            finalStatus = FinalStatus.ONGOING;
        } else if (winner == 0) {
            finalStatus = FinalStatus.DRAW;
        } else {
            finalStatus = FinalStatus.WIN;
        }

        // Return the response
        return new WinnerResponse(finalStatus, winner);
    }

    @Override
    public GeneralResponse newGame(int boardSize) throws InvalidBoardException {
        // Setting the win count
        if (ticTacToe.getWinner() == 1) {
            ticTacToe.setPlayer1WinCount(ticTacToe.getPlayer1WinCount() + 1);
        } else if (ticTacToe.getWinner() == 2){
            ticTacToe.setPlayer2WinCount(ticTacToe.getPlayer2WinCount() + 1);
        }
        // Reset the winner, state, and the board of the game
        ticTacToe.setWinner(0);
        ticTacToe.setFinal(false);
        setupBoard(boardSize);

        // Return the result
        return ticTacToe.convertToActionResponse();
    }

    @Override
    public String getBoard() throws InvalidBoardException {
        return ticTacToe.getBoardState();
    }

    private boolean isBoardFilled(Board board) throws InvalidBoardException {
        Integer boardSize = 0;
        try {
            boardSize = board.getValue()[0].length;
        } catch (Exception e) {
            throw new InvalidBoardException();
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getValue()[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setupBoard(Integer boardSize) throws InvalidBoardException {
        ticTacToe.setBoardSize(boardSize);
        Board arrayBoard = new Board(new Integer[boardSize][boardSize]); // Create a 2D array of Integer objects
        // Initialize all elements to 0
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                arrayBoard.getValue()[i][j] = 0; // Set each element to 0
            }
        }
        ticTacToe.setBoard(arrayBoard);
        ticTacToe.setWinningCondition(boardSize);
    }

    private void checkForWinner(Integer col, Integer row) {
        // Extracting the diagonals
        logger.info("checkForWinner");
        List<List<Integer>> diagonals = GameUtil.getDiagonals(ticTacToe.getBoard(), row, col);
        Integer gameWinner;
        boolean isWinnerSet = false;
        logger.info("ticTacToe.getWinningCondition() :" + ticTacToe.getWinningCondition());
        for (int i = 0; i < 2; i++) {
            Integer[] diagonal = diagonals.get(i).toArray(new Integer[0]);
            gameWinner = GameUtil.checkConsecutive(diagonal, ticTacToe.getWinningCondition());
            if (gameWinner != 0) {
                isWinnerSet = true;
                ticTacToe.setWinner(gameWinner);
                break;
            }
        }
        // Search for vertical and horizontal winner
        if (!isWinnerSet) {
            // Checking horizontally
            Integer[] horizontalLine = ticTacToe.getBoard().getValue()[row];
            gameWinner = GameUtil.checkConsecutive(horizontalLine, ticTacToe.getWinningCondition());
            if (gameWinner != 0) {
                isWinnerSet = true;
                ticTacToe.setWinner(gameWinner);
            } else {
                Integer[] verticalLine = GameUtil.getCertainColumn(ticTacToe.getBoard(), col);
                gameWinner = GameUtil.checkConsecutive(verticalLine, ticTacToe.getWinningCondition());
                if (gameWinner != 0) {
                    isWinnerSet = true;
                    ticTacToe.setWinner(gameWinner);
                }
            }
        }
        ticTacToe.setFinal(isWinnerSet);
    }
}
