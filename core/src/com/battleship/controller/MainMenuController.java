package com.battleship.controller;

public class MainMenuController{
    /**
     * The singleton instance of this controller
     */
    private static MainMenuController instance;

    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 100;

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 50;


    /**
     * Creates a new GameController that controls the physics of a certain GameModel.
     *
     */
    private MainMenuController() {
    }

    /**
     * Returns a singleton instance of a main menu controller
     *
     * @return the singleton instance
     */
    public static MainMenuController getInstance() {
        if (instance == null){
            instance = new MainMenuController();
        }
        return instance;
    }
}
