package com.battleship.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {

    private final int size;
    CellType[][] map;
//    ArrayList<Ship> ships;
    HashMap<Ship, Coord> ships;

    Board(int size){
        this.size = size;
        this.map = new CellType[size][size];
        this.fillMap(CellType.Free);

//        this.ships = new ArrayList<Ship>();
        this.ships = new HashMap<Ship, Coord>();
    }

    public int getSize(){
        return this.size;
    }

    public CellType getCell(Coord pos){
        return this.map[pos.getY()][pos.getX()];
    }

    public void setCell(Coord pos, CellType cellType){
        this.map[pos.getY()][pos.getX()] = cellType;
    }

    public void addShip(Ship ship, Coord pos){
        this.ships.put(ship, pos);
    }

    public HashMap<Ship, Coord> getShips(){
        return this.ships;
    }

    public void fillMap(CellType cellType){
        for(CellType[] row : this.map){
            Arrays.fill(row, cellType);
        }
    }
}
