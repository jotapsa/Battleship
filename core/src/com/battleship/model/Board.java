package com.battleship.model;

import com.battleship.model.aux.CellType;
import com.battleship.model.aux.ShipType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A model representing a Board.
 */
public class Board {

    private final int size;
    private CellType[][] map;
    private ArrayList<Ship> ships;
    private HashMap<Ship, Coord> shipsPlaced;

    /**
     * Instantiates a new Board.
     *
     * @param size int
     */
    public Board(int size){
        this.size = size;
        this.map = new CellType[size][size];
        this.fillMap(CellType.Free);

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Carrier));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Battleship));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Cruiser));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Cruiser));
        this.ships.add(new Ship(com.battleship.model.aux.ShipType.Submarine));
        this.ships.add(new Ship(ShipType.Submarine));

        this.shipsPlaced = new HashMap<Ship, Coord>();
    }

    /**
     * Gets board size.
     *
     * @return int size
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Gets cell.
     *
     * @param pos Coord
     * @return CellType cell
     */
    public CellType getCell(Coord pos){
        return this.map[pos.getY()][pos.getX()];
    }

    /**
     * Sets cell of board.
     *
     * @param pos Coord
     * @param cellType CellType
     */
    public void setCell(Coord pos, CellType cellType){
        this.map[pos.getY()][pos.getX()] = cellType;
    }

    /**
     * Place a ship.
     *
     * @param ship Ship
     * @param pos Coord
     */
    public void addShip(Ship ship, Coord pos){
        this.shipsPlaced.put(ship, pos);
    }

    /**
     * Gets ships ArrayList.
     *
     * @return ships ArrayList
     */
    public ArrayList<Ship> getShips(){
        return this.ships;
    }

    /**
     * Gets placedShips HashMap.
     *
     * @return HashMap placedShips
     */
    public HashMap<Ship, Coord> getPlacedShips(){
        return this.shipsPlaced;
    }

    /**
     * Fill map with cellType
     *
     * @param cellType CellType
     */
    public void fillMap(CellType cellType){
        for(CellType[] row : this.map){
            Arrays.fill(row, cellType);
        }
    }
}
