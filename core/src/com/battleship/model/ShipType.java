package com.battleship.model;

public enum ShipType {
    Carrier(5), Battleship(4), Cruiser(3), Submarine(2), Boat(1);

    private int size;

     ShipType(int size) {
        this.size = size;
    }

    public int getSize(){
         return this.size;
    }
}
