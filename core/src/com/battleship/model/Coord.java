package com.battleship.model;

public class Coord {
    private int x;

    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return int X component of a coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return int Y component of a coordinate
     */
    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

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
