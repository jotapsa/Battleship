package com.battleship.model;

public class Ship {

    private ShipType type;
    private boolean[] ship;
    private int timesHit;

    private Orientation orientation;
    private boolean isPlaced;
    private boolean sank;

    Ship(ShipType type) {
        this.type= type;
        this.ship = new boolean[type.getSize()]; //array will be initialized to false when it's alocated
        this.orientation = Orientation.Vertical;

        this.timesHit = 0;
        this.sank = false;
        this.isPlaced = false;
    }

    public ShipType getShipType(){
        return this.type;
    }

    public boolean getIsPlaced(){
        return this.isPlaced;
    }

    public Orientation getOrientation(){
        return this.orientation;
    }

    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }

    public void hit (int pos){
        this.ship[pos] = true;
        this.timesHit++;

        if(this.ship.length == this.timesHit){
            this.sank = true;
        }
    }
}
