package com.battleship.model;

import com.battleship.model.aux.Turn;

import java.util.ArrayList;

public class Player {
    private Turn turnToPlay;

    private ArrayList<Coord> hitCells;
    private ArrayList<Ship> destroyedShips;

    Player(Turn turnToPlay){
        this.turnToPlay = turnToPlay;
    }

    public Turn getTurnToPlay(){
        return this.turnToPlay;
    }

    public ArrayList<Coord> getHitCells() {
        return this.hitCells;
    }



}
