package com.battleship.model.entities;

public class Ship extends EntityModel {

    private int size;
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
        super();

        this.size = size;
        this.timesHit = 0;
        this.sank = false;
        this.isPlaced = false;
    }


}
