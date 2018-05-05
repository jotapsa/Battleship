package com.battleship.controller;

import com.badlogic.gdx.Game;
import com.battleship.model.GameModel;

public class GameController {

    private static GameController instance;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private GameController() {
    }

    /**
     * Returns a singleton instance of a game controller
     *
     * @return the singleton instance
     */
    public static GameController getInstance() {
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public void handleClick(int x, int y) {

    }

    public void changeTurn(){

    }
}
