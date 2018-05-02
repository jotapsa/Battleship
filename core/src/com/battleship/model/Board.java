package com.battleship.model;

import java.util.ArrayList;

public class Board {

    private final int size;
    int[][] map;
    ArrayList<Ship> ships;

    Board(int size){
        this.size = size;
        this.map = new int[size][size];

        this.ships = new ArrayList<Ship>();
    }

    public int getSize(){
        return this.size;
    }

    public void addShip(Ship ship){
        this.ships.add(ship);
    }
}
