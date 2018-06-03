package com.battleship.model;

import com.battleship.model.aux.Turn;

/**
 * A model representing a Human Player.
 */
public class Human extends Player{

    /**
     * Instantiates a new Human Model.
     *
     * @param turnToPlay Turn
     */
    public Human(Turn turnToPlay){
        super(turnToPlay);
    }
}
