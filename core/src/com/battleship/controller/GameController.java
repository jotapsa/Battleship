package com.battleship.controller;

import com.battleship.model.Computer;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Move;
import com.battleship.model.Turn;

import java.util.Random;

public class GameController {
    private static GameController instance;

    private GameModel gameModel;
    private BoardController boardController;
    private boolean isGameOver;

    /**
     * Creates a new GameController of a certain GameModel.
     *
     */
    public GameController(GameModel gameModel) {

        this.gameModel = gameModel;
        this.boardController = BoardController.getInstance();
        isGameOver = false;

        instance = this;
    }

    /**
     * Returns a singleton instance of a board controller
     *
     * @return the singleton instance
     */
    public static GameController getInstance() {
        return instance;
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public void update(float delta){
        if(gameModel.getPlayerBlue() instanceof Computer || gameModel.getPlayerRed() instanceof Computer){
            computerPlay();
        }

        isGameOver = boardController.allSank();
    }

    private void computerPlay() {
        Move move = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Computer){
            move = ((Computer) gameModel.getPlayerBlue()).createMove(gameModel.getPlayerRedBoard());
            boardController.setBoard(gameModel.getPlayerRedBoard());
        }

        if(gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Computer){
            move = ((Computer) gameModel.getPlayerRed()).createMove(gameModel.getPlayerBlueBoard());
            boardController.setBoard(gameModel.getPlayerBlueBoard());
        }

        if(move != null){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }
    }

    public boolean isValidTarget(Coord target){
        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human){
            boardController.setBoard(gameModel.getPlayerRedBoard());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human){
            boardController.setBoard(gameModel.getPlayerBlueBoard());
        }

        return boardController.isValidTarget(target);
    }


    public void handleClick(Coord target){
        Move move = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human){
            move = new Move(target, gameModel.getTurn());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human){
            move = new Move(target, gameModel.getTurn());
        }

        if(move != null){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }

        isGameOver = boardController.allSank();
    }

    public boolean isGameOver(){
        return isGameOver;
    }
}
