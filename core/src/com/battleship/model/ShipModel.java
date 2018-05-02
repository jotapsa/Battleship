package com.battleship.model;

public enum ShipModel {
    Carrier(5), Battleship(4), Cruiser(3), Submarine(2);

    private int size;

     ShipModel(int size) {
        this.size = size;
    }

    int getSize(){
         return this.size;
    }
}
