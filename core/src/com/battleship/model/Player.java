package com.battleship.model;

import java.util.ArrayList;

public class Player {

    private static int boardSize = 10;

    private Board board;
    private ArrayList<Ship> ships;

    Player(){
        this.board = new Board(boardSize);

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(ShipType.Carrier));
        this.ships.add(new Ship(ShipType.Battleship));
        this.ships.add(new Ship(ShipType.Cruiser));
        this.ships.add(new Ship(ShipType.Submarine));
        this.ships.add(new Ship(ShipType.Submarine));
    }

    public Board getBoard(){
        return this.board;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }
}
