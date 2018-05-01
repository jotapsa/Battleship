package com.battleship.model.entities;

public abstract class EntityModel {
    /**
     * The x-coordinate of this model.
     */
    private float x;

    /**
     * The y-coordinate of this model.
     */
    private float y;

    /**
     * Constructs a model with a position.
     *
     * @param x The x-coordinate of this entity.
     * @param y The y-coordinate of this entity.
     */
    EntityModel(int x, int y, float rotation) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a model without a position.
     *
     */
    EntityModel() {
        this.x = 0;
        this.y = 0;
    }


    /**
     * Returns the x-coordinate of this entity.
     *
     * @return The x-coordinate of this entity.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this entity.
     *
     * @return The y-coordinate of this entity.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the position of this entity.
     *
     * @param x The x-coordinate of this entity.
     * @param y The y-coordinate of this entity.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
