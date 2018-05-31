package com.battleship.model;

public class Move {
    private Coord target;
    private Turn turn;

    public Move(Coord target, Turn turn){
        this.target = target;
        this.turn = turn;
    }

    public Turn getTurn(){
        return this.turn;
    }

    public Coord getTarget(){
        return target;
    }
}
