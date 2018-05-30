package com.battleship.controller;

import com.battleship.model.GameModel;
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


    public void handleClick(int x, int y) {
        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Player){

        }

    }

    public void changeTurn(){

    }
}
