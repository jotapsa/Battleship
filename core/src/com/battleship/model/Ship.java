package com.battleship.model;

import com.battleship.model.aux.Orientation;
import com.battleship.model.aux.ShipType;

/**
 * A model representing a Ship.
 */
public class Ship {

    private com.battleship.model.aux.ShipType type;
    private boolean[] ship;
    private int timesHit;

    private Orientation orientation;
    private boolean isPlaced;
    private boolean sank;

    /**
     * Instantiates a new Ship.
     *
     * @param type ShipType
     */
    Ship(ShipType type) {
        this.type= type;
        this.ship = new boolean[type.getSize()]; //array will be initialized to false when it's alocated
        this.orientation = Orientation.Horizontal;

        this.timesHit = 0;
        this.sank = false;
        this.isPlaced = false;
    }

    /**
     * Gets ship type.
     *
     * @return ShipType ship type
     */
    public ShipType getShipType(){
        return this.type;
    }

    /**
     * Checks if ship is sank.
     *
     * @return boolean true, if is sank, false otherwise.
     */
    public boolean isSank() { return this.sank;}

    /**
     * Gets ship orientation.
     *
     * @return Orientation orientation
     */
    public Orientation getOrientation(){
        return this.orientation;
    }

    /**
     * Sets ship isPlaced.
     *
     * @param placed boolean
     */
    public void setIsPlaced(boolean placed){
        this.isPlaced = placed;
    }

    /**
     * Sets ship orientation.
     *
     * @param orientation Orientation
     */
    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }

    /**
     * Flip ship orientation.
     */
    public void flipOrientation() {
        if(this.orientation == Orientation.Vertical){
            this.orientation =  Orientation.Horizontal;
        }
        else{
            this.orientation = Orientation.Vertical;
        }
    }

    /**
     * Hit Ship.
     */
    public void hit (){
        this.timesHit++;

        if(this.ship.length == this.timesHit){
            this.sank = true;
        }
    }
}
