package com.battleship.model;

import com.battleship.model.aux.Turn;

import java.util.ArrayList;

/**
 * A model representing a Player.
 */
public class Player {
    private Turn turnToPlay;

    /**
     * Instantiates a new Player.
     *
     * @param turnToPlay Turn
     */
    Player(Turn turnToPlay){
        this.turnToPlay = turnToPlay;
    }

    /**
     * Gets turn to play.
     *
     * @return Turn turn
     */
    public Turn getTurnToPlay(){
        return this.turnToPlay;
    }
}
