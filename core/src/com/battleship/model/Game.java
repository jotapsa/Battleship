package com.battleship.model;

/**
 * A model representing a game.
 */

public class Game {
    private Player p1;
    private Player p2;

    private Turn turn;

    Game(){
        this.turn = Turn.Blue;
    }

    public Turn getTurn(){
        return this.turn;
    }

    public void changeTurn(){

    }
}
