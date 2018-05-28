package com.battleship.controller;

import com.badlogic.gdx.math.Vector2;

public class PlacingController{
    /**
     * The singleton instance of this controller
     */
    private static PlacingController instance;

    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 100;

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 50;


    /**
     * Creates a new PlacingController that controls the physics of a certain GameModel.
     *
     */
    private PlacingController() {
    }

    /**
     * Returns a singleton instance of a main menu controller
     *
     * @return the singleton instance
     */
    public static PlacingController getInstance() {
        if (instance == null){
            instance = new PlacingController();
        }
        return instance;
    }
}
