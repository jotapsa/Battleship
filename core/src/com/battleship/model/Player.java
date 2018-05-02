package com.battleship.model;

import java.util.ArrayList;

public class Player {

    private static int boardSize = 10;

    private Board board;
    private ArrayList<Ship> ships;

    Player(){
        this.board = new Board(boardSize);

        this.ships = new ArrayList<Ship>();
        this.ships.add(new Ship(ShipModel.Carrier));
        this.ships.add(new Ship(ShipModel.Battleship));
        this.ships.add(new Ship(ShipModel.Cruiser));
        this.ships.add(new Ship(ShipModel.Submarine));
        this.ships.add(new Ship(ShipModel.Submarine));
    }

    public Board getBoard(){
        return this.board;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }
}
