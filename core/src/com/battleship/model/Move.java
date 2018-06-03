package com.battleship.model;

import com.battleship.model.aux.Orientation;
import com.battleship.model.aux.Turn;

/**
 * A model representing a Move.
 */
public class Move {
    private boolean processed;
    public boolean processedVerticalPos;
    public boolean processedVerticalNeg;
    public boolean processedHorizontalPos;
    public boolean processedHorizontalNeg;


    private Orientation orientation = Orientation.randomOrientation();
    private int wayMultiplier = (int) Math.floor(Math.random()*2)*2-1;

    private Turn turn;
    private Coord target;
    private boolean hitShip;

    /**
     * Instantiates a new Move.
     *
     * @param target Coord
     * @param turn Turn
     */
    public Move(Coord target, Turn turn){
        this.processed = false;
        this.processedVerticalPos = false;
        this.processedVerticalNeg = false;
        this.processedHorizontalPos = false;
        this.processedHorizontalNeg = false;

        this.turn = turn;
        this.target = target;
        this.hitShip = false; //default
    }

    /**
     * Instantiates a new Move.
     *
     * @param target Coord
     * @param turn Turn
     * @param processed boolean
     */
    public Move(Coord target, Turn turn, boolean processed){
        this.processed = processed;
        this.processedVerticalPos = processed;
        this.processedVerticalNeg = processed;
        this.processedHorizontalPos = processed;
        this.processedHorizontalNeg = processed;

        this.turn = turn;
        this.target = target;
        this.hitShip = false; //default
    }

    /**
     * Checks if is processed.
     *
     * @return boolean true, if is processed, false otherwise.
     */
    public boolean isProcessed(){
        return this.processed;
    }

    /**
     * Gets move orientation.
     *
     * @return Orientation orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Gets way multiplier.
     *
     * @return int way multiplier
     */
    public int getWayMultiplier(){
        return wayMultiplier;
    }

    /**
     * Gets move turn.
     *
     * @return Turn turn
     */
    public Turn getTurn(){
        return this.turn;
    }

    /**
     * Gets move target.
     *
     * @return Coord target
     */
    public Coord getTarget(){
        return target;
    }

    /**
     * Checks if move hit any ship.
     *
     * @return boolean hit ship
     */
    public boolean getHitShip(){
        return hitShip;
    }

    /**
     * Flip orientation.
     */
    public void flipOrientation() {
        switch(orientation){
            case Vertical:
                orientation = Orientation.Horizontal;
                break;
            case Horizontal:
                orientation = Orientation.Vertical;
                break;
            default:
                break;
        }
    }

    /**
     * Flip.
     */
    public void flip(){
        switch(orientation){
            case Horizontal:
                if(wayMultiplier == 1){
                    this.processedHorizontalPos = true;
                }else{
                    this.processedHorizontalNeg = true;
                }

                if(this.processedHorizontalPos && this.processedHorizontalNeg){
                    checkEofProcessing();
                    flipOrientation();
                }
                else{
                    this.wayMultiplier *= -1;
                }

                break;
            case Vertical:
                if(wayMultiplier == 1){
                    this.processedVerticalPos = true;
                }else{
                    this.processedVerticalNeg = true;
                }

                if(this.processedVerticalPos && this.processedVerticalNeg){
                    checkEofProcessing();
                    flipOrientation();
                }
                else{
                    this.wayMultiplier *= -1;
                }

                break;
            default:
                break;
        }
    }

    /**
     * Check End of processing.
     */
    public void checkEofProcessing(){
        if(this.processedVerticalPos && this.processedVerticalNeg && this.processedHorizontalNeg && this.processedHorizontalPos){
            this.processed = true;
        }
    }

    /**
     * Sets hit ship.
     *
     * @param hitShip boolean
     */
    public void setHitShip(boolean hitShip){
        this.hitShip = hitShip;
    }
}
