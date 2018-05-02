package com.battleship.model;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;

import java.util.ArrayList;

public class Board {

    private final int size;
    CellType[][] map;
    ArrayList<Ship> ships;

    Board(int size){
        this.size = size;
        this.map = new CellType[size][size];

        this.ships = new ArrayList<Ship>();
    }

    public int getSize(){
        return this.size;
    }

    public CellType getCell(Coord pos){
        return this.map[pos.getY()][pos.getX()];
    }

    public void addShip(Ship ship){
        this.ships.add(ship);
    }
}
