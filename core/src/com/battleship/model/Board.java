package com.battleship.model;

import com.battleship.model.aux.CellType;
import com.battleship.model.aux.ShipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {

    private final int size;
    private com.battleship.model.aux.CellType[][] map;
    private ArrayList<Ship> ships;
    private HashMap<Ship, Coord> shipsPlaced;

    Board(int size){
        this.size = size;
        this.map = new com.battleship.model.aux.CellType[size][size];
        this.fillMap(com.battleship.model.aux.CellType.Free);

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Carrier));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Battleship));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Cruiser));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Cruiser));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Submarine));
        this.ships.add(new Ship(ShipType.Submarine));

        this.shipsPlaced = new HashMap<Ship, Coord>();
    }

    public int getSize(){
        return this.size;
    }

    public com.battleship.model.aux.CellType getCell(Coord pos){
        return this.map[pos.getY()][pos.getX()];
    }

    public void setCell(Coord pos, com.battleship.model.aux.CellType cellType){
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

    public void fillMap(com.battleship.model.aux.CellType cellType){
        for(CellType[] row : this.map){
            Arrays.fill(row, cellType);
        }
    }
}
