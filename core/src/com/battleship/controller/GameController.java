package com.battleship.controller;

import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Move;
import com.battleship.model.Player;
import com.battleship.model.Turn;

public class GameController {
    private GameModel gameModel;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }


    public void handleClick(Coord coord){
        Move move = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Player){
            move = new Move(coord, gameModel.getTurn());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Player){
            move = new Move(coord, gameModel.getTurn());
        }

        if(move != null /*&& check valid move */){
            //do move
            //force consecutive moves

        }



    }
}
