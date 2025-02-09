package com.wfd.tictactoe.controller;

import com.google.gson.Gson;
import com.wfd.tictactoe.dto.GeneralResponse;
import com.wfd.tictactoe.dto.MoveRequest;
import com.wfd.tictactoe.dto.WinnerResponse;
import com.wfd.tictactoe.exception.*;
import com.wfd.tictactoe.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tic-tac-toe")
@CrossOrigin(origins = "*") // Allow all port cross origin
public class GameController {

    private static Logger logger = LogManager.getLogger("gameContollerLog");

    @Autowired
    GameService gameService;

    @Autowired
    Gson gson;

    // Initialize the game, reset all game progress
    // Param: boardSize (the size of the new game)
    @GetMapping("/initialize/{boardSize}")
    public GeneralResponse initialize(@PathVariable("boardSize") Integer boardSize) throws InvalidBoardException {
        logger.info("[Initialize] Request created for boardSize :" + boardSize);
        GeneralResponse response = gameService.initializeGame(boardSize);
        logger.info("[Initialize] Response :" + gson.toJson(response));
        return response;
    }

    // Initialize the game, but keep the score
    // Param: boardSize (the size of the new game)
    @GetMapping("/newGame/{boardSize}")
    public GeneralResponse newGame(@PathVariable("boardSize") Integer boardSize) throws InvalidBoardException {
        logger.info("[NewGame] Request created for boardSize :" + boardSize);
        GeneralResponse response = gameService.newGame(boardSize);
        logger.info("[NewGame] Response :" + gson.toJson(response));
        return response;
    }

    // Command from one player to occupy certain coordinate
    @PostMapping("/move")
    public GeneralResponse initialize(@RequestBody MoveRequest moveRequest) throws PlayerUnknownException, InvalidMoveException, WrongPlayerMoveException, InvalidBoardException, GameOverException {
        logger.info("[Move] Request :" + gson.toJson(moveRequest));
        GeneralResponse response = gameService.makeMove(moveRequest);
        logger.info("[Move] Response :" + gson.toJson(response));
        return response;
    }

    // Return the winner and current state of the game
    @GetMapping("/getWinner")
    public WinnerResponse getWinner() {
        logger.info("[GetWinner] Request received");
        WinnerResponse response = gameService.getWinner();
        logger.info("[GetWinner] Response :" + gson.toJson(response));
        return response;
    }

    // Debug purpose only - Get the board of the game
    @GetMapping("/getCurrentBoard")
    public String getBoard() throws InvalidBoardException {
        logger.info("[GetCurrentBoard] Request received");
        String response = gameService.getBoard();
        logger.info("[GetCurrentBoard] Response :" + gameService.getBoard());
        return response;
    }
}
