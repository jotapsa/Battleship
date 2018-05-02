package com.battleship.model;

public class Ship {

    private ShipModel model;
    private boolean[] ship;
    private int timesHit;

    private Orientation orientation;
    private boolean isPlaced;
    private boolean sank;

    Ship(ShipModel model) {
        this.model = model;
        this.ship = new boolean[model.getSize()];
        this.orientation = Orientation.Vertical;

        this.timesHit = 0;
        this.sank = false;
        this.isPlaced = false;
    }

    public Orientation getOrientation(){
        return this.orientation;
    }
}
