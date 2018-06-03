package com.battleship.model;

/**
 * A model representing a Coord.
 */
public class Coord {
    private int x;

    private int y;
    
    /**
	 * Instantiates a new Coord with coordinates x and y.
	 *
	 * @param x int coordinate of the newly constructed Coord.
	 * @param y int coordinate of the newly constructed Coord.
	 */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
	 * Gets X coordinate.
	 *
	 * @return int x coordinate of this Coord.
	 */
    public int getX() {
        return this.x;
    }

    /**
	 * Gets Y coordinate.
	 *
	 * @return int y coordinate of this Coord.
	 */
    public int getY() {
        return this.y;
    }
    
    /**
	 * Sets X coordinate.
	 *
	 * @param x coordinate.
	 */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
	 * Sets Y coordinate.
	 *
	 * @param y coordinate.
	 */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if( o == this){
            return true;
        }

        if(!(o instanceof Coord)){
            return false;
        }

        Coord coord = (Coord) o;

        return (coord.getX() == getX() &&
        coord.getY() == getY());
    }

    @Override
    public String toString(){
        return "" + x + " " + y;
    }
}
