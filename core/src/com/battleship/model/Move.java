package com.battleship.model;

public class Move {
    private Coord target;
    private Turn turn;
    private boolean hitShip;

    public Move(Coord target, Turn turn){
        this.target = target;
        this.turn = turn;
        this.hitShip = false; //default
    }

    public Turn getTurn(){
        return this.turn;
    }

    public Coord getTarget(){
        return target;
    }

    public boolean getHitShip(){
        return hitShip;
    }

    public void setHitShip(boolean hitShip){
        this.hitShip = hitShip;
    }
}
