package com.battleship.controller;

import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Move;
import com.battleship.model.Turn;

public class GameController {
    private GameModel gameModel;
    private BoardController boardController;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.boardController = BoardController.getInstance();
    }


    public void handleClick(Coord target){
        Move move = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human){
            move = new Move(target, gameModel.getTurn());
            boardController.setBoard(gameModel.getPlayerRedBoard());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human){
            move = new Move(target, gameModel.getTurn());
            boardController.setBoard(gameModel.getPlayerBlueBoard());
        }

        if(move != null && boardController.isValidTarget(target)){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }
    }
}
