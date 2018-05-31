package com.battleship.model;

import java.util.ArrayList;

public class Player {
    private Turn turnToPlay;
    private ArrayList<Ship> ships;

    private ArrayList<Coord> hitCells;
    private ArrayList<Ship> destroyedShips;

    Player(Turn turnToPlay){
        this.turnToPlay = turnToPlay;

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(ShipType.Carrier));
        this.ships.add(new Ship(ShipType.Battleship));
        this.ships.add(new Ship(ShipType.Cruiser));
        this.ships.add(new Ship(ShipType.Submarine));
        this.ships.add(new Ship(ShipType.Submarine));
    }

    public Turn getTurnToPlay(){
        return this.turnToPlay;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }

}
