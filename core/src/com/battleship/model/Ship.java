package com.battleship.model;

import com.battleship.model.aux.Orientation;
import com.battleship.model.aux.ShipType;

public class Ship {

    private com.battleship.model.aux.ShipType type;
    private boolean[] ship;
    private int timesHit;

    private Orientation orientation;
    private boolean isPlaced;
    private boolean sank;

    Ship(com.battleship.model.aux.ShipType type) {
        this.type= type;
        this.ship = new boolean[type.getSize()]; //array will be initialized to false when it's alocated
        this.orientation = Orientation.Horizontal;

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

    public boolean isSank() { return this.sank;}

    public Orientation getOrientation(){
        return this.orientation;
    }

    public void setIsPlaced(boolean placed){
        this.isPlaced = placed;
    }

    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }

    public void flipOrientation() {
        if(this.orientation == Orientation.Vertical){
            this.orientation =  Orientation.Horizontal;
        }
        else{
            this.orientation = Orientation.Vertical;
        }
    }

    public void hit (){
        this.timesHit++;

        if(this.ship.length == this.timesHit){
            this.sank = true;
        }
    }
}
