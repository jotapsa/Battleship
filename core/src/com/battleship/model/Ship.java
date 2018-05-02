package com.battleship.model;

public class Ship {

    private int size;
    private boolean[] ship;

    private int orientation;
    private int timesHit;
    private boolean isPlaced;
    private boolean sank;

    /**
     * Constructs a model with a position.
     *
     * @param size
     */
    Ship(int size) {
        this.size = size;
        this.ship = new boolean[size];

        this.timesHit = 0;
        this.sank = false;
        this.isPlaced = false;
    }


}
