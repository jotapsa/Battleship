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
     * The current rotation of this model in radians.
     */
    private float rotation;


    /**
     * Constructs a model with a position.
     *
     * @param x The x-coordinate of this entity.
     * @param y The y-coordinate of this entity.
     * @param rotation The current rotation of this entity in radians.
     */
    EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Constructs a model without a position.
     *
     */
    EntityModel() {
        this.x = 0;
        this.y = 0;
        this.rotation = 0;
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
     * Returns the rotation of this entity.
     *
     * @return The rotation of this entity in radians.
     */
    public float getRotation() {
        return rotation;
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

    /**
     * Sets the rotation of this entity.
     *
     * @param rotation The current rotation of this entity in radians.
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
