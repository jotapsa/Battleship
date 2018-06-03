package com.battleship.model.aux;

public enum CellType {
    Free("FREE"),
    FreeHit("FREEHIT"),
    Ship("SHIP"),
    ShipHit("SHIPHIT");

    private String type;

    CellType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }
}
