package com.battleship.controller;

public class GameController {

    private static GameController instance;

    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    GameController() {
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

    /**
     * Calculates the next physics step of duration delta (in seconds).
     *
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {

    }

    public void changeTurn(){

    }
}
