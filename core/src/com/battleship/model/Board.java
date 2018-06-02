package com.battleship.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {

    private final int size;
    private CellType[][] map;
    private ArrayList<Ship> ships;
    private HashMap<Ship, Coord> shipsPlaced;

    Board(int size){
        this.size = size;
        this.map = new CellType[size][size];
        this.fillMap(CellType.Free);

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(ShipType.Carrier));
        this.ships.add(new Ship(ShipType.Battleship));
        this.ships.add(new Ship(ShipType.Cruiser));
        this.ships.add(new Ship(ShipType.Cruiser));
        this.ships.add(new Ship(ShipType.Submarine));
        this.ships.add(new Ship(ShipType.Submarine));

        this.shipsPlaced = new HashMap<Ship, Coord>();
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
        this.shipsPlaced.put(ship, pos);
    }

    public ArrayList<Ship> getShips(){
        return this.ships;
    }

    public HashMap<Ship, Coord> getPlacedShips(){
        return this.shipsPlaced;
    }

    public void fillMap(CellType cellType){
        for(CellType[] row : this.map){
            Arrays.fill(row, cellType);
        }
    }
}
